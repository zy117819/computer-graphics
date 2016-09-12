// Info.cpp: implementation of the Info class.
//
//////////////////////////////////////////////////////////////////////

#include "Info.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////
#include "Tetris.h"
extern Tetris m_block;

extern int score;
extern int level;
extern int lines;

Info::Info()
{
	GdiplusStartupInput gdiplusStartupInput;
	GdiplusStartup(&gdiplusToken, &gdiplusStartupInput, NULL);

	m_bkcolor.SetFromCOLORREF(RGB(20,30,40));
	SolidBrush brush(m_bkcolor);
	Pen pen(m_bkcolor);
	m_bkbrush=brush.Clone();
	m_bkpen=pen.Clone();
}

Info::~Info()
{
	GdiplusShutdown(gdiplusToken);
}

void Info::SetWnd(HWND wnd)
{
	infownd=wnd;
	infodc=GetDC(infownd);
	m_graphics=Graphics::FromHDC(infodc);

	RECT rect;
	GetClientRect(infownd,&rect);

	mainrect.X=rect.left;
	mainrect.Y=rect.top;
	mainrect.Width=rect.right-rect.left;
	mainrect.Height=rect.bottom-rect.top;

	frect.X=mainrect.X;
	frect.Y=mainrect.Width/2;
	frect.Width=mainrect.Width/3;
	frect.Height=mainrect.Height-mainrect.Width/2;

	prect.X=mainrect.X;
	prect.Y=mainrect.Y;
	prect.Width=mainrect.Width;
	prect.Height=mainrect.Width/2;

	slrect.X=mainrect.Width/3;
	slrect.Y=mainrect.Width/2;
	slrect.Width=mainrect.Width*2/3;
	slrect.Height=mainrect.Height-mainrect.Width/2;

	LoadImage();
}

void Info::DrawDC()
{
	m_graphics->Clear(m_bkcolor);

	DrawFloor();

	DrawPreview();

	DrawSL();
}

void Info::LoadImage()
{
	m_pre=Image::FromFile(L"Data/gl/b10.bmp");

	m_floor[0]=Image::FromFile(L"Data/info/floor.png");
	int i;
	unsigned short filename[50];
	for(i=1;i<=10;i++)
	{
		swprintf(filename,L"Data/info/f%d.png",i);
		m_floor[i]=Image::FromFile(filename);
	}
}

void Info::DrawFloor()
{
	Bitmap floorbmp(m_floor[0]->GetWidth(),m_floor[0]->GetHeight());
	Graphics floordc(&floorbmp);

	floordc.DrawImage(m_floor[0],0,0,m_floor[0]->GetWidth(),m_floor[0]->GetHeight());

	int i;
	int height=m_block.GetAlHeight();
	int x=6,y=8+(10-height)*18;

	if(height!=0)
	{
		if(height>1)
		{
			for(i=height;i>=2;i--)
			{
				floordc.DrawImage(m_floor[i],x,y,22,18);
				y+=18;
			}
		}
		floordc.DrawImage(m_floor[1],x,y,22,23);
	}


	POINT3D* p;
	int count=m_block.GetCurrent(&p);

	Pen pen(Color(255,255,255),1);
	x=10;
	for(i=0;i<=count-1;i++)
	{
		y=11-(p[i].z+1)*18;
		floordc.DrawRectangle(&pen,x,y,13,9);
	}

	m_graphics->DrawImage(&floorbmp,frect);
}

void Info::DrawPreview()
{
	Bitmap prebmp(200,100);
	Graphics predc(&prebmp);
	predc.Clear(m_bkcolor);

	int bound=5;
	int w=prebmp.GetWidth()-2*bound;
	int h=prebmp.GetHeight()-2*bound;
	float sizex=w/4;
	float sizey=h/2;
	float x=sizex+bound;
	float y=sizey+bound;

	PointF* p;
	int i;
	int count=m_block.GetPreBlock(&p);
	for(i=0;i<=count-1;i++)
	{	
		predc.DrawImage(m_pre,x+sizex*p[i].X,y+sizey*p[i].Y,sizex,sizey);
	}
	m_graphics->DrawImage(&prebmp,prect);
}

void Info::DrawSL()
{
	int width=200,height=400,bound=20;
	int fw=33,fh=60;
	Bitmap slbmp(width,height);
	Graphics sldc(&slbmp);
	sldc.Clear(m_bkcolor);

	Font font1(L"宋体",fh,FontStyleBold,UnitPixel);
	Font font2(L"宋体",fh,FontStyleRegular,UnitPixel);
	SolidBrush brush(Color(0,255,0));

	WCHAR out[6][10];
	swprintf(out[0],L"等 级");
	swprintf(out[2],L"行 数");
	swprintf(out[4],L"得 分");
	swprintf(out[1],L"%d",level);
	swprintf(out[3],L"%d",lines);
	swprintf(out[5],L"%d",score);

	int i;
	PointF point[6];
	for(i=0;i<=5;i+=2)
	{
		point[i].X=fw/2;
		point[i].Y=bound+i*fh;
	}
	for(i=1;i<=5;i+=2)
	{
		point[i].X=(width-fw*wcslen(out[i]))/2;
		point[i].Y=bound+i*fh;
	}

	for(i=0;i<=5;i++)
	{
		sldc.DrawString(out[i],-1,&font2,point[i],&brush);
	}

	m_graphics->DrawImage(&slbmp,slrect);
}
