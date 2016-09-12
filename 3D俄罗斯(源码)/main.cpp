#include"all_header.h"
#include"OpenGL.h"
#include"Tetris.h"
#include"Info.h"
#define TIME_START 3000

int width=840,height=600;
HWND mainwnd,glwnd,infownd;

bool pause=false;
bool over=false;
bool run=true;

int level=1;
int score=0;
int lines=0;
int flushtime=TIME_START/level;
bool keys[256];

OpenGL m_gl;
Info m_info;
Tetris m_block;

HANDLE threadredraw;
DWORD threadid;

LRESULT CALLBACK InfoWndProc(HWND hWnd,UINT message,WPARAM wParam,LPARAM lParam);
LRESULT CALLBACK MainWndProc(HWND hWnd,UINT message,WPARAM wParam,LPARAM lParam);
LRESULT CALLBACK GLWndProc(HWND hWnd,UINT message,WPARAM wParam,LPARAM lParam);
void ShowInfo(HDC dc);
DWORD WINAPI ReDrawScene(LPVOID lpParam=0);
void KeyProc(WPARAM wParam,LPARAM lParam);
void ReStart();
void AfterDown();

int WINAPI WinMain(HINSTANCE hInstance,HINSTANCE hPreInstance,LPSTR lpCmdLine,int nShowCmd)
{
	WNDCLASS wc,glwc,infowc;
	wc.style=CS_HREDRAW|CS_VREDRAW|CS_OWNDC;
	wc.lpfnWndProc=MainWndProc;
	wc.cbClsExtra=0;
	wc.cbWndExtra=0;
	wc.hInstance=hInstance;
	wc.hIcon=LoadIcon(NULL,IDI_APPLICATION);
	wc.hCursor=LoadCursor(NULL,IDC_ARROW);
	wc.hbrBackground=NULL;
	wc.lpszMenuName=NULL;
	wc.lpszClassName="MainFrame";

	glwc=wc;
	glwc.lpszClassName="GL";
	glwc.lpfnWndProc=GLWndProc;

	infowc=wc;
	infowc.lpszClassName="info";
	infowc.lpfnWndProc=InfoWndProc;

	if (!RegisterClass(&wc)||!RegisterClass(&glwc)||!RegisterClass(&infowc))		// 尝试注册窗口类
	{
		MessageBox(NULL,"注册窗口失败","错误",MB_OK|MB_ICONEXCLAMATION);
		return FALSE;							// 退出并返回FALSE
	}
 
	int x=GetSystemMetrics(SM_CXSCREEN)/2-width/2;
	int y=GetSystemMetrics(SM_CYSCREEN)/2-height/2;

	mainwnd=CreateWindow("MainFrame","3D俄罗斯方块",
						 WS_SYSMENU|WS_MINIMIZEBOX,
						 x,y,width,height,
						 NULL,NULL,hInstance,NULL);

	RECT mainrect;
	GetClientRect(mainwnd,&mainrect);

	glwnd=CreateWindow("GL","",WS_CHILD,
						mainrect.left,mainrect.top,
						mainrect.bottom,mainrect.bottom,
						mainwnd,NULL,hInstance,NULL);

	infownd=CreateWindow("info","",WS_CHILD,
						mainrect.bottom,mainrect.top,
						mainrect.right-mainrect.bottom ,mainrect.bottom,
						mainwnd,NULL,hInstance,NULL);

	ShowWindow(mainwnd,SW_SHOW);
	ShowWindow(glwnd,SW_SHOW);
	ShowWindow(infownd,SW_SHOW);
	SetFocus(mainwnd);

	m_gl.Start(glwnd);
	m_info.SetWnd(infownd);

	DWORD threadid;
	ZeroMemory(keys,sizeof(keys));
	threadredraw=CreateThread(NULL,0,ReDrawScene,0,0,&threadid);

	MSG msg;
	while(run)
	{
		if (PeekMessage(&msg,NULL,0,0,PM_REMOVE))			// 有消息在等待吗?
		{
			if (msg.message==WM_QUIT)				// 收到退出消息?
			{
				run=false;					// 是，则done=TRUE
			}
			else							// 不是，处理窗口消息
			{
				TranslateMessage(&msg);				// 翻译消息
				DispatchMessage(&msg);				// 发送消息
			}
		}
	}

	return msg.wParam;
}

LRESULT CALLBACK MainWndProc(HWND hWnd,UINT message,WPARAM wParam,LPARAM lParam)
{
	switch(message)
	{
	case WM_DESTROY:
		{
			PostQuitMessage(0);
			break;
		}
	case WM_KEYDOWN:
		{
			KeyProc(wParam,lParam);
			break;
		}
	case WM_KEYUP:
		{
			keys[wParam]=false;
			break;
		}
	}
	return DefWindowProc(hWnd,message,wParam,lParam);
}

LRESULT CALLBACK GLWndProc(HWND hWnd,UINT message,WPARAM wParam,LPARAM lParam)
{
	switch(message)
	{
	case WM_PAINT:
		{
			LPPAINTSTRUCT lpPaint;
			BeginPaint(hWnd,lpPaint);
			m_gl.DrawScene();
			EndPaint(hWnd,lpPaint);
			m_info.DrawFloor();
			break;
		}
	}
	return DefWindowProc(hWnd,message,wParam,lParam);
}

LRESULT CALLBACK InfoWndProc(HWND hWnd,UINT message,WPARAM wParam,LPARAM lParam)
{
	switch(message)
	{
	case WM_PAINT:
		{
			LPPAINTSTRUCT lpPaint;
			BeginPaint(hWnd,lpPaint);
			m_info.DrawDC();
			EndPaint(hWnd,lpPaint);
			break;
		}
	}
	return DefWindowProc(hWnd,message,wParam,lParam);
}

DWORD WINAPI ReDrawScene(LPVOID lpParam)
{
	if(over)
	{
		int retry=MessageBox(mainwnd,"重新开始?","华丽地...你挂了!!!",MB_YESNO|MB_ICONQUESTION);
		if(retry==IDYES)
		{
			ReStart();	
		}
		else
		{
			PostMessage(mainwnd,WM_DESTROY,NULL,NULL);
		}
		return 0;
	}

	Sleep(flushtime);

	if(!m_block.Move(0,0,-1))
	{
		AfterDown();
	}
	PostMessage(glwnd,WM_PAINT,NULL,NULL);
	threadredraw=CreateThread(NULL,0,ReDrawScene,0,0,&threadid);
	return 0;
}

void ShowInfo(HDC dc)
{
	HBRUSH brush=CreateSolidBrush(RGB(rand()%256,rand()%256,rand()%256));
	HBRUSH oldbrush=(HBRUSH)SelectObject(dc,brush);
	Rectangle(dc,0,0,113.6,113.6);
	SelectObject(dc,oldbrush);
}

void KeyProc(WPARAM wParam,LPARAM lParam)
{
	if(pause&&(wParam!=VK_SPACE))
	{
		return;
	}

	if(wParam==VK_SPACE)
	{
		pause=!pause;
		if(pause)
		{
			TerminateThread(threadredraw,0);
		}
		else
		{
			threadredraw=CreateThread(NULL,0,ReDrawScene,0,0,&threadid);
		}
		return;
	}

/*	if(wParam==VK_UP||wParam==VK_DOWN||wParam==VK_LEFT||wParam==VK_RIGHT)
	{
		if(keys[wParam]==true)
		{
			if(keys[VK_CONTROL]||keys[VK_SHIFT])
			{
				return;
			}
		}
		else
		{
			keys[wParam]=true;
		}
	}
	else
	{
		keys[wParam]=true;
	}*/

	if(keys[wParam])
	{
		return;
	}

	keys[wParam]=true;

	if(keys[VK_CONTROL]&&keys[VK_SHIFT])
	{
		return;
	}

	bool redraw=false;
	switch(wParam)
	{
	case VK_UP:
		{
			if(keys[VK_CONTROL])
			{
				redraw=m_block.Move(0,0,-1);
			}
			else if(keys[VK_SHIFT])
			{
				redraw=m_block.Rotate(1,0,0);
			}
			else
			{
				redraw=m_block.Move(0,1,0);
			}
			break;
		}
	case VK_DOWN:
		{
			if(keys[VK_CONTROL])
			{
				TerminateThread(threadredraw,0);
				int s=m_block.Down();
				score+=s*level/5;
				AfterDown();
				threadredraw=CreateThread(NULL,0,ReDrawScene,0,0,&threadid);
				redraw=true;
			}
			else if(keys[VK_SHIFT])
			{
				redraw=m_block.Rotate(-1,0,0);
			}
			else
			{
				redraw=m_block.Move(0,-1,0);
			}
			break;
		}
	case VK_LEFT:
		{
			if(keys[VK_CONTROL])
			{
				redraw=m_block.Rotate(0,0,-1);
			}
			else if(keys[VK_SHIFT])
			{
				redraw=m_block.Rotate(0,1,0);
			}
			else
			{
				redraw=m_block.Move(-1,0,0);
			}
			break;
		}
	case VK_RIGHT:
		{
			if(keys[VK_CONTROL])
			{
				redraw=m_block.Rotate(0,0,1);
			}
			else if(keys[VK_SHIFT])
			{
				redraw=m_block.Rotate(0,-1,0);
			}
			else
			{
				redraw=m_block.Move(1,0,0);
			}
			break;
		}
	}

	if(redraw)
	{
		PostMessage(glwnd,WM_PAINT,NULL,NULL);
	}
}

void ReStart()
{
	over=false;
	ZeroMemory(keys,sizeof(keys));
	m_block.Rebuild();
	PostMessage(glwnd,WM_PAINT,NULL,NULL);
	PostMessage(infownd,WM_PAINT,NULL,NULL);
	threadredraw=CreateThread(NULL,0,ReDrawScene,0,0,&threadid);
}

void AfterDown()
{
	int s=m_block.DelAlready();

	if(s>0)
	{
		score+=s*(s+1)/2*level*5;
		lines+=s;
		level=lines/10+1;
		flushtime=TIME_START/level;
	}
	m_info.DrawSL();

	int already[XY_MAX][XY_MAX][Z_MAX];
	m_block.GetAlready(already);
	if(already[X_CENTER-1][Y_CENTER-1][0]==1)
	{
		over=true;
	}
	else
	{
		m_block.Reset();
		m_info.DrawPreview();
	}
}