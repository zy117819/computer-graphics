// Tetris.h: interface for the Tetris class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_TETRIS_H__15565725_DE3F_48EF_ADE8_F93FAB662274__INCLUDED_)
#define AFX_TETRIS_H__15565725_DE3F_48EF_ADE8_F93FAB662274__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "all_header.h"
#define TETRIS_MAX 4
#define TETRIS_KIND 5

typedef struct
{
	int x;
	int y;
	int z;
}POINT3D;


typedef struct
{
	POINT3D center;
	POINT3D other[TETRIS_MAX-1];
}TetrisModel;

class Tetris  
{
public:
	int GetPreBlock(PointF** point);
	int GetAlHeight();
	void Rebuild();
	int Down();
	int DelAlready();
	void GetAlready(int already[XY_MAX][XY_MAX][Z_MAX]);
	bool Rotate(int x,int y,int z);
	int GetCurrent(POINT3D** point);
	bool Move(int x,int y,int z);
	void Reset();
	Tetris();
	virtual ~Tetris();
private:
	int m_preid;
	bool CheckR(POINT3D* p,int n,POINT3D* pmove);
	int m_id;
	bool Check(POINT3D* p,int n);
	int m_already[XY_MAX][XY_MAX][Z_MAX];
	TetrisModel m_model[TETRIS_KIND];
	TetrisModel m_current;

	void CreateModel();
};
#endif // !defined(AFX_TETRIS_H__15565725_DE3F_48EF_ADE8_F93FAB662274__INCLUDED_)
