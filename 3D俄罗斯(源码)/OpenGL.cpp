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
	int nPixelFormat;					  // 象素点格式
	PIXELFORMATDESCRIPTOR pfd = { 
	    sizeof(PIXELFORMATDESCRIPTOR),    // pfd结构的大小 
	    1,                                // 版本号 
	    PFD_DRAW_TO_WINDOW |              // 支持在窗口中绘图 
	    PFD_SUPPORT_OPENGL |              // 支持 OpenGL 
	    PFD_DOUBLEBUFFER,                 // 双缓存模式 
	    PFD_TYPE_RGBA,                    // RGBA 颜色模式 
	    24,                               // 24 位颜色深度 
	    0, 0, 0, 0, 0, 0,                 // 忽略颜色位 
	    0,                                // 没有非透明度缓存 
	    0,                                // 忽略移位位 
	    0,                                // 无累加缓存 
	    0, 0, 0, 0,                       // 忽略累加位 
	    16,                               // 16 位深度缓存     
	    0,                                // 无模板缓存 
	    0,                                // 无辅助缓存 
	    PFD_MAIN_PLANE,                   // 主层 
	    0,                                // 保留 
	    0, 0, 0                           // 忽略层,可见性和损毁掩模 
	}; 
	if (!(nPixelFormat = ChoosePixelFormat(m_gldc, &pfd)))
		{ MessageBox(NULL,"没找到合适的显示模式","Error",MB_OK|MB_ICONEXCLAMATION);
	      return FALSE;
		}
	SetPixelFormat(m_gldc,nPixelFormat,&pfd);//设置当前设备的像素点格式
	m_glrc = wglCreateContext(m_gldc);          //获取渲染描述句柄
	wglMakeCurrent(m_gldc, m_glrc);             //激活渲染描述句柄	return true;
}

void OpenGL::InitGLWnd(int width,int height)
{
	glViewport(0,0,width,height);			// 设置OpenGL视口大小。	
	glMatrixMode(GL_PROJECTION);			// 设置当前矩阵为投影矩阵。
	glLoadIdentity();						// 重置当前指定的矩阵为单位矩阵
	gluPerspective							// 设置透视图
		( m_povy,							// 透视角设置为 45 度
		  (GLfloat)width/(GLfloat)height,	// 窗口的宽与高比
		  m_premove-1,								// 视野透视深度:近点1.0f
		  Z_MAX*m_size+m_premove+1				// 视野透视深度:始点0.1f远点1000.0f
		);
	glMatrixMode(GL_MODELVIEW);				// 设置当前矩阵为模型视图矩阵
	glLoadIdentity();						// 重置当前指定的矩阵为单位矩阵
	glTranslatef(-X_CENTER*m_size,-Y_CENTER*m_size,-m_premove);
}

void OpenGL::DrawScene()
{
	glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);// 刷新背景
	glDisable(GL_TEXTURE_2D);
	glCallList(m_bklist);
	DrawAlready();
	DrawBlock();
	SwapBuffers(m_gldc);								 // 切换缓冲区
}

void OpenGL::Setup()
{
	CreateList();
	glClearColor(0.1,0.1,0.1,1);			 // 设置刷新背景色
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
		glNormal3f(0,0,1);					// 法线指向观察者
		glTexCoord2f(0,0); glVertex3f(-length,-length,length);	
		glTexCoord2f(1,0); glVertex3f(length,-length, length);	
		glTexCoord2f(1,1); glVertex3f(length, length, length);	
		glTexCoord2f(0,1); glVertex3f(-length, length, length);	
		// 后侧面
		glNormal3f(0,0,-1);					// 法线背向观察者
		glTexCoord2f(1,0); glVertex3f(-length,-length,-length);	
		glTexCoord2f(1,1); glVertex3f(-length, length,-length);	
		glTexCoord2f(0,1); glVertex3f(length, length,-length);	
		glTexCoord2f(0,0); glVertex3f(length,-length,-length);	
		// 顶面
		glNormal3f(0,1,0);					// 法线向上
		glTexCoord2f(0,1); glVertex3f(-length, length,-length);	
		glTexCoord2f(0,0); glVertex3f(-length, length, length);	
		glTexCoord2f(1,0); glVertex3f(length, length, length);	
		glTexCoord2f(1,1); glVertex3f(length, length,-length);	
		// 底面
		glNormal3f(0,-1,0);					// 法线朝下
		glTexCoord2f(1,1); glVertex3f(-length,-length,-length);	
		glTexCoord2f(0,1); glVertex3f(length,-length,-length);	
		glTexCoord2f(0,0); glVertex3f(length,-length, length);	
		glTexCoord2f(1,0); glVertex3f(-length,-length, length);	
		// 右侧面
		glNormal3f(1,0,0);					// 法线朝右
		glTexCoord2f(1,0); glVertex3f(length,-length,-length);	
		glTexCoord2f(1,1); glVertex3f(length, length,-length);	
		glTexCoord2f(0,1); glVertex3f(length, length, length);	
		glTexCoord2f(0,0); glVertex3f(length,-length, length);	
		// 左侧面
		glNormal3f(-1,0,0);					// 法线朝左
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
