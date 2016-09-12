// OpenGL.h: interface for the OpenGL class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_OPENGL_H__46AA0ED9_F948_45D1_B2AE_318CFD8AE8B9__INCLUDED_)
#define AFX_OPENGL_H__46AA0ED9_F948_45D1_B2AE_318CFD8AE8B9__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include"all_header.h"

#define GL_PI 3.14159

class OpenGL  
{
public:
	GLfloat m_size;
	OpenGL();
	virtual ~OpenGL();
	void Start(HWND glwnd);

	void DrawScene();
private:
	GLvoid* bmpbits;
	GLuint m_texture[Z_MAX];
	void CreateTexture();
	BITMAPINFOHEADER LoadGLBitmap(char *filename);
	void DrawAlready();
	void CreateBlock();
	GLuint m_blocklist;
	GLuint m_bklist;
	void DrawBlock();
	GLfloat m_premove;
	GLdouble m_povy;
	void CreateBackground();
	void CreateList();
	void Setup();
	RECT m_glrect;
	HWND m_glwnd;
	HGLRC m_glrc;
	HDC m_gldc;
	void InitGLWnd(int width,int height);
	BOOL CreateGLWnd();
};

#endif // !defined(AFX_OPENGL_H__46AA0ED9_F948_45D1_B2AE_318CFD8AE8B9__INCLUDED_)
