// OpenGL.cpp: implementation of the OpenGL class.
//
//////////////////////////////////////////////////////////////////////
#include "OpenGL.h"
//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////
#include "Tetris.h"
extern Tetris m_block;

OpenGL::OpenGL()
{
	m_povy=37;
}

OpenGL::~OpenGL()
{

}

void OpenGL::Start(HWND glwnd)
{
	m_glwnd=glwnd;
	m_gldc=GetDC(glwnd);
	GetClientRect(m_glwnd,&m_glrect);
	m_size=(GLfloat)m_glrect.right/XY_MAX;
	m_premove=(m_glrect.bottom-m_glrect.top)/2/tan(m_povy/2/180*GL_PI);

	CreateGLWnd();
	InitGLWnd(m_glrect.right,m_glrect.bottom);
	Setup();
	DrawScene();
}

BOOL OpenGL::CreateGLWnd()
{
	int nPixelFormat;					  // ���ص��ʽ
	PIXELFORMATDESCRIPTOR pfd = { 
	    sizeof(PIXELFORMATDESCRIPTOR),    // pfd�ṹ�Ĵ�С 
	    1,                                // �汾�� 
	    PFD_DRAW_TO_WINDOW |              // ֧���ڴ����л�ͼ 
	    PFD_SUPPORT_OPENGL |              // ֧�� OpenGL 
	    PFD_DOUBLEBUFFER,                 // ˫����ģʽ 
	    PFD_TYPE_RGBA,                    // RGBA ��ɫģʽ 
	    24,                               // 24 λ��ɫ��� 
	    0, 0, 0, 0, 0, 0,                 // ������ɫλ 
	    0,                                // û�з�͸���Ȼ��� 
	    0,                                // ������λλ 
	    0,                                // ���ۼӻ��� 
	    0, 0, 0, 0,                       // �����ۼ�λ 
	    16,                               // 16 λ��Ȼ���     
	    0,                                // ��ģ�建�� 
	    0,                                // �޸������� 
	    PFD_MAIN_PLANE,                   // ���� 
	    0,                                // ���� 
	    0, 0, 0                           // ���Բ�,�ɼ��Ժ������ģ 
	}; 
	if (!(nPixelFormat = ChoosePixelFormat(m_gldc, &pfd)))
		{ MessageBox(NULL,"û�ҵ����ʵ���ʾģʽ","Error",MB_OK|MB_ICONEXCLAMATION);
	      return FALSE;
		}
	SetPixelFormat(m_gldc,nPixelFormat,&pfd);//���õ�ǰ�豸�����ص��ʽ
	m_glrc = wglCreateContext(m_gldc);          //��ȡ��Ⱦ�������
	wglMakeCurrent(m_gldc, m_glrc);             //������Ⱦ�������	return true;
}

void OpenGL::InitGLWnd(int width,int height)
{
	glViewport(0,0,width,height);			// ����OpenGL�ӿڴ�С��	
	glMatrixMode(GL_PROJECTION);			// ���õ�ǰ����ΪͶӰ����
	glLoadIdentity();						// ���õ�ǰָ���ľ���Ϊ��λ����
	gluPerspective							// ����͸��ͼ
		( m_povy,							// ͸�ӽ�����Ϊ 45 ��
		  (GLfloat)width/(GLfloat)height,	// ���ڵĿ���߱�
		  m_premove-1,								// ��Ұ͸�����:����1.0f
		  Z_MAX*m_size+m_premove+1				// ��Ұ͸�����:ʼ��0.1fԶ��1000.0f
		);
	glMatrixMode(GL_MODELVIEW);				// ���õ�ǰ����Ϊģ����ͼ����
	glLoadIdentity();						// ���õ�ǰָ���ľ���Ϊ��λ����
	glTranslatef(-X_CENTER*m_size,-Y_CENTER*m_size,-m_premove);
}

void OpenGL::DrawScene()
{
	glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);// ˢ�±���
	glDisable(GL_TEXTURE_2D);
	glCallList(m_bklist);
	DrawAlready();
	DrawBlock();
	SwapBuffers(m_gldc);								 // �л�������
}

void OpenGL::Setup()
{
	CreateList();
	glClearColor(0.1,0.1,0.1,1);			 // ����ˢ�±���ɫ
	glCullFace(GL_BACK);

	glEnable(GL_DEPTH_TEST);
	glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);

	glEnable(GL_TEXTURE_2D);
	CreateTexture();
}

void OpenGL::CreateList()
{
	m_bklist=glGenLists(1);
	m_blocklist=glGenLists(2);

	glNewList(m_bklist,GL_COMPILE);
	CreateBackground();
	glEndList();

	glNewList(m_blocklist,GL_COMPILE);
	CreateBlock();
	glEndList();
}

void OpenGL::CreateBackground()
{
	int i;
	float xymax=XY_MAX;
	float zmax=Z_MAX;
	GLfloat oldcolor[4];
	glGetFloatv(GL_CURRENT_COLOR,oldcolor);
	glColor3f(0,0.7,0.7);

	glPushMatrix();
	glTranslatef(X_CENTER*m_size,Y_CENTER*m_size,0);

	for(i=0;i<=zmax;i++)
	{
		glBegin(GL_LINE_LOOP);
		{
			glVertex3f(-m_size*xymax/2,-m_size*xymax/2,-i*m_size);
			glVertex3f(-m_size*xymax/2,m_size*xymax/2,-i*m_size);
			glVertex3f(m_size*xymax/2,m_size*xymax/2,-i*m_size);
			glVertex3f(m_size*xymax/2,-m_size*xymax/2,-i*m_size);
		}
		glEnd();
	}

	for(i=0;i<=xymax;i++)
	{
		glBegin(GL_LINE_STRIP);
		{
			glVertex3f(-m_size*xymax/2,m_size*xymax/2-i*m_size,0);
			glVertex3f(-m_size*xymax/2,m_size*xymax/2-i*m_size,-zmax*m_size);
			glVertex3f(m_size*xymax/2,m_size*xymax/2-i*m_size,-zmax*m_size);
			glVertex3f(m_size*xymax/2,m_size*xymax/2-i*m_size,0);
		}
		glEnd();

		glBegin(GL_LINE_STRIP);
		{
			glVertex3f(m_size*xymax/2-i*m_size,-m_size*xymax/2,0);
			glVertex3f(m_size*xymax/2-i*m_size,-m_size*xymax/2,-zmax*m_size);
			glVertex3f(m_size*xymax/2-i*m_size,m_size*xymax/2,-zmax*m_size);
			glVertex3f(m_size*xymax/2-i*m_size,m_size*xymax/2,0);
		}
		glEnd();
	}

	glColor4fv(oldcolor);
	glPopMatrix();
}

void OpenGL::CreateBlock()
{
	float length=m_size/2;
	glBegin(GL_QUADS);
	{
		glNormal3f(0,0,1);					// ����ָ��۲���
		glTexCoord2f(0,0); glVertex3f(-length,-length,length);	
		glTexCoord2f(1,0); glVertex3f(length,-length, length);	
		glTexCoord2f(1,1); glVertex3f(length, length, length);	
		glTexCoord2f(0,1); glVertex3f(-length, length, length);	
		// �����
		glNormal3f(0,0,-1);					// ���߱���۲���
		glTexCoord2f(1,0); glVertex3f(-length,-length,-length);	
		glTexCoord2f(1,1); glVertex3f(-length, length,-length);	
		glTexCoord2f(0,1); glVertex3f(length, length,-length);	
		glTexCoord2f(0,0); glVertex3f(length,-length,-length);	
		// ����
		glNormal3f(0,1,0);					// ��������
		glTexCoord2f(0,1); glVertex3f(-length, length,-length);	
		glTexCoord2f(0,0); glVertex3f(-length, length, length);	
		glTexCoord2f(1,0); glVertex3f(length, length, length);	
		glTexCoord2f(1,1); glVertex3f(length, length,-length);	
		// ����
		glNormal3f(0,-1,0);					// ���߳���
		glTexCoord2f(1,1); glVertex3f(-length,-length,-length);	
		glTexCoord2f(0,1); glVertex3f(length,-length,-length);	
		glTexCoord2f(0,0); glVertex3f(length,-length, length);	
		glTexCoord2f(1,0); glVertex3f(-length,-length, length);	
		// �Ҳ���
		glNormal3f(1,0,0);					// ���߳���
		glTexCoord2f(1,0); glVertex3f(length,-length,-length);	
		glTexCoord2f(1,1); glVertex3f(length, length,-length);	
		glTexCoord2f(0,1); glVertex3f(length, length, length);	
		glTexCoord2f(0,0); glVertex3f(length,-length, length);	
		// �����
		glNormal3f(-1,0,0);					// ���߳���
		glTexCoord2f(0,0); glVertex3f(-length,-length,-length);	
		glTexCoord2f(1,0); glVertex3f(-length,-length, length);	
		glTexCoord2f(1,1); glVertex3f(-length, length, length);	
		glTexCoord2f(0,1); glVertex3f(-length, length,-length);	
	}
	glEnd();
}

void OpenGL::DrawBlock()
{
	GLfloat color[4];
	glGetFloatv(GL_CURRENT_COLOR,color);
	GLint polygonmode[1];
	glGetIntegerv(GL_POLYGON_MODE,polygonmode);

	glColor3f(1,1,1);
	glPolygonMode(GL_FRONT_AND_BACK,GL_LINE);
	glDisable(GL_TEXTURE_2D);

	int x,y,z;
	POINT3D* p;
	int count=m_block.GetCurrent(&p);
	for(int i=0;i<=count-1;i++)
	{
		float x=p[i].x;
		float y=p[i].y;
		float z=p[i].z;

		glPushMatrix();
		glTranslatef(m_size*x,m_size*y,m_size*z+m_size/2+0.01);
		glCallList(m_blocklist);
		glPopMatrix();
	}

	glColor4fv(color);
	glEnable(GL_TEXTURE_2D);
	glPolygonMode(GL_FRONT_AND_BACK,polygonmode[0]);
}


void OpenGL::DrawAlready()
{
	GLfloat color[4];
	glGetFloatv(GL_CURRENT_COLOR,color);
	
	glEnable(GL_TEXTURE_2D);
	glColor3f(1,1,1);
	
	int already[XY_MAX][XY_MAX][Z_MAX];
	m_block.GetAlready(already);
	int x,y,z;
	for(x=0;x<=XY_MAX-1;x++)
		for(y=0;y<=XY_MAX-1;y++)
			for(z=0;z<=Z_MAX-1;z++)
			{
				glBindTexture(GL_TEXTURE_2D,m_texture[Z_MAX-1-z]);
				if(already[x][y][z]==1)
				{
						glPushMatrix();
						glTranslatef(m_size*(x+1),m_size*(y+1),-m_size*(z+1)+m_size/2);
						glCallList(m_blocklist);
						glPopMatrix();
				}
			}
	glColor4fv(color);
}

BITMAPINFOHEADER OpenGL::LoadGLBitmap(char *filename)
{
	FILE* fp;
	BITMAPFILEHEADER fileheader;
	BITMAPINFO info;
	BITMAPINFOHEADER header;
	int infosize,bitsize;

	if((fp=fopen(filename,"rb"))==NULL)
		return header;

	fread(&fileheader,sizeof(BITMAPFILEHEADER),1,fp);

	if(fileheader.bfType!='MB')
	{
		fclose(fp);
		return header;
	}

	infosize=fileheader.bfOffBits-sizeof(BITMAPFILEHEADER);
	fread(&info,1,infosize,fp);
	header=info.bmiHeader;
	if((bitsize=info.bmiHeader.biSizeImage)==0)
		bitsize=(info.bmiHeader.biWidth*info.bmiHeader.biBitCount+7)/8*
		abs(info.bmiHeader.biHeight);

	bmpbits=malloc(bitsize);
	fread(bmpbits,1,bitsize,fp);
	fclose(fp);
	return header;
}

void OpenGL::CreateTexture()
{
	glGenTextures(Z_MAX,m_texture);
	int i;
	char filename[50];
	BITMAPINFOHEADER bmpheader;
	for(i=0;i<=Z_MAX-1;i++)
	{
		sprintf(filename,"Data/gl/b%d.bmp",i+1);
		bmpheader=LoadGLBitmap(filename);
		glBindTexture(GL_TEXTURE_2D,m_texture[i]);
		glTexImage2D(GL_TEXTURE_2D,0,3,bmpheader.biWidth,bmpheader.biHeight,0,GL_BGR_EXT,GL_UNSIGNED_BYTE,bmpbits);
		glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MAG_FILTER,GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER,GL_LINEAR);
		/*glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER,GL_LINEAR_MIPMAP_NEAREST);
		gluBuild2DMipmaps(GL_TEXTURE_2D,3,bmpheader.biWidth,bmpheader.biHeight,GL_BGR_EXT,GL_UNSIGNED_BYTE,bmpbits);*/
	}
}
