// Info.h: interface for the Info class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_INFO_H__114B985D_EFEB_4D26_A685_31EFAF703064__INCLUDED_)
#define AFX_INFO_H__114B985D_EFEB_4D26_A685_31EFAF703064__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "all_header.h"

class Info  
{
public:
	void DrawPreview();
	void DrawSL();
	void DrawFloor();
	void DrawDC();
	void SetWnd(HWND wnd);
	Info();
	virtual ~Info();

private:
	Graphics* m_graphics;
	RectF slrect;
	Color m_bkcolor;
	Pen* m_bkpen;
	Brush* m_bkbrush;
	Image* m_pre;
	RectF prect;
	void LoadImage();
	Image* m_floor[11];
	RectF frect;
	RectF mainrect;
	HWND infownd;
	HDC infodc;
	ULONG_PTR gdiplusToken;
};

#endif // !defined(AFX_INFO_H__114B985D_EFEB_4D26_A685_31EFAF703064__INCLUDED_)
