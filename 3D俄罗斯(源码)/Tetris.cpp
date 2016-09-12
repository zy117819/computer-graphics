// Tetris.cpp: implementation of the Tetris class.
//
//////////////////////////////////////////////////////////////////////

#include "Tetris.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////
Tetris::Tetris()
{
	srand(time(NULL));
	m_preid=rand()%5;
	CreateModel();
	Rebuild();
}

Tetris::~Tetris()
{

}

void Tetris::CreateModel()
{
	int i,j;
	for(i=0;i<=TETRIS_KIND-1;i++)
	{
		m_model[i].center.x=X_CENTER;
		m_model[i].center.y=Y_CENTER;
		m_model[i].center.z=-1;
		for(j=0;j<=TETRIS_MAX-2;j++)
		{
			m_model[i].other[j].z=0;
		}
	}

	m_model[0].other[0].x=0;
	m_model[0].other[0].y=1;
	m_model[0].other[1].x=1;
	m_model[0].other[1].y=1;
	m_model[0].other[2].x=1;
	m_model[0].other[2].y=0;

	m_model[1].other[0].x=0;
	m_model[1].other[0].y=1;
	m_model[1].other[1].x=-1;
	m_model[1].other[1].y=0;
	m_model[1].other[2].x=1;
	m_model[1].other[2].y=0;

	m_model[2].other[0].x=-1;
	m_model[2].other[0].y=1;
	m_model[2].other[1].x=-1;
	m_model[2].other[1].y=0;
	m_model[2].other[2].x=1;
	m_model[2].other[2].y=0;

	m_model[3].other[0].x=-1;
	m_model[3].other[0].y=1;
	m_model[3].other[1].x=0;
	m_model[3].other[1].y=1;
	m_model[3].other[2].x=1;
	m_model[3].other[2].y=0;

	m_model[4].other[0].x=-1;
	m_model[4].other[0].y=0;
	m_model[4].other[1].x=1;
	m_model[4].other[1].y=0;
	m_model[4].other[2].x=2;
	m_model[4].other[2].y=0;
}

void Tetris::Reset()
{
	m_id=m_preid;
	m_preid=rand()%5;
	m_current=m_model[m_id];
}

int Tetris::GetCurrent(POINT3D** point)
{
	(*point)=(POINT3D*)malloc(TETRIS_MAX*sizeof(POINT3D));
	(*point)[0]=m_current.center;
	int i;
	for(i=0;i<=TETRIS_MAX-2;i++)
	{
		(*point)[i+1].x=m_current.center.x+m_current.other[i].x;
		(*point)[i+1].y=m_current.center.y+m_current.other[i].y;
		(*point)[i+1].z=m_current.center.z+m_current.other[i].z;
	}
	return TETRIS_MAX;
}

bool Tetris::Move(int x,int y,int z)
{
	int i;
	POINT3D p[TETRIS_MAX];

	p[0]=m_current.center;

	p[0].x+=x;
	p[0].y+=y;
	p[0].z+=z;

	for(i=0;i<=TETRIS_MAX-2;i++)
	{
		p[i+1].x=m_current.other[i].x+p[0].x;
		p[i+1].y=m_current.other[i].y+p[0].y;
		p[i+1].z=m_current.other[i].z+p[0].z;
	}

	POINT3D pmove;
	if(Check(p,TETRIS_MAX))
	{
		m_current.center=p[0];
		return true;
	}
	else if(z==-1)
	{
		p[0]=m_current.center;
		for(i=0;i<=TETRIS_MAX-2;i++)
		{
			p[i+1].x=m_current.other[i].x+p[0].x;
			p[i+1].y=m_current.other[i].y+p[0].y;
			p[i+1].z=m_current.other[i].z+p[0].z;
		}

		for(i=0;i<=TETRIS_MAX-1;i++)
		{
			m_already[p[i].x-1][p[i].y-1][-p[i].z-1]=1;
		}

		return false;
	}
}

bool Tetris::Rotate(int x, int y, int z)
{
	if((x*x+y*y+z*z)!=1)
	{
		return false;
	}

	if(z!=0&&m_id==0&&m_current.other[1].z==0)
	{
		return false;
	}

	int i;
	POINT3D p[TETRIS_MAX];
	
	p[0]=m_current.center;
	for(i=1;i<=TETRIS_MAX-1;i++)
	{
		p[i]=m_current.other[i-1];
	}
	
	int s;
	if(x!=0)
	{
		for(i=1;i<=TETRIS_MAX-1;i++)
		{
			s=p[i].z;
			p[i].z=-x*p[i].y;
			p[i].y=x*s;
		}
	}

	if(y!=0)
	{
		for(i=1;i<=TETRIS_MAX-1;i++)
		{
			s=p[i].x;
			p[i].x=-y*p[i].z;
			p[i].z=y*s;
		}
	}

	if(z!=0)
	{
		for(i=1;i<=TETRIS_MAX-1;i++)
		{
			s=p[i].y;
			p[i].y=-z*p[i].x;
			p[i].x=z*s;
		}
	}

	POINT3D ps[TETRIS_MAX];
	ps[0]=p[0];
	for(i=1;i<=TETRIS_MAX-1;i++)
	{
		ps[i].x=p[i].x+p[0].x;
		ps[i].y=p[i].y+p[0].y;
		ps[i].z=p[i].z+p[0].z;
	}

	POINT3D pmove;
	if(CheckR(ps,TETRIS_MAX,&pmove))
	{
		for(i=1;i<=TETRIS_MAX-1;i++)
		{
			m_current.other[i-1]=p[i];
		}
		Move(pmove.x,pmove.y,pmove.z);
		return true;
	}
	else
	{
		return false;
	}
}

bool Tetris::Check(POINT3D *p, int n)
{
	int i;
	for(i=0;i<=n-1;i++)
	{
		if(m_already[p[i].x-1][p[i].y-1][-p[i].z-1]==1)
			return false;
	}

	for(i=0;i<=n-1;i++)
	{
		if((p[i].x<1)||(p[i].x>XY_MAX))
		{
			return false;
		}
		if((p[i].y<1)||(p[i].y>XY_MAX))
		{
			return false;
		}
		if((p[i].z<-Z_MAX)||(p[i].z>-1))
		{
			return false;
		}
	}
	return true;
}

bool Tetris::CheckR(POINT3D *p, int n, POINT3D *pmove)
{
	int i;
	for(i=0;i<=n-1;i++)
	{
		if((p[i].x<1)||(p[i].x>XY_MAX)||(p[i].y<1)||(p[i].y>XY_MAX)||(p[i].z>-1)||(p[i].z<-Z_MAX))
		{
			continue;
		}

		if(m_already[p[i].x-1][p[i].y-1][-p[i].z-1]==1)
		{
			return false;
		}
	}

	ZeroMemory(pmove,sizeof(*pmove));

	for(i=0;i<=n-1;i++)
	{
		if(p[i].x<1&&(1-p[i].x)>pmove->x)
		{
			pmove->x=1-p[i].x;
		}
		else if(p[i].x>XY_MAX&&(XY_MAX-p[i].x)<pmove->x)
		{
			pmove->x=XY_MAX-p[i].x;
		}

		if(p[i].y<1&&(1-p[i].y)>pmove->y)
		{
			pmove->y=1-p[i].y;
		}
		else if(p[i].y>XY_MAX&&(XY_MAX-p[i].y)<pmove->y)
		{
			pmove->y=XY_MAX-p[i].y;
		}

		if(p[i].z<-Z_MAX&&(-Z_MAX-p[i].y)>pmove->z)
		{
			pmove->z=-Z_MAX-p[i].z;
		}
		else if(p[i].z>-1&&(-1-p[i].z)<pmove->z)
		{
			pmove->z=-1-p[i].z;
		}
	}
	return true;
}

void Tetris::GetAlready(int already[XY_MAX][XY_MAX][Z_MAX])
{
	memcpy(already,m_already,sizeof(m_already));
}

int Tetris::DelAlready()
{
	int count=0;
	bool del=true;
	int x,y,z,zu;
	for(z=-Z_MAX;z<=-1&&count<=3;z++)
	{
		del=true;

		for(x=0;x<=XY_MAX-1;x++)
			for(y=0;y<=XY_MAX-1;y++)
				if(m_already[x][y][-z-1]==0)
				{
					del=false;
					break;
				}

		if(del)
		{
			count++;
			for(zu=z;zu<=-2;zu++)
				for(x=0;x<=XY_MAX-1;x++)
					for(y=0;y<=XY_MAX-1;y++)
					{
						m_already[x][y][-zu-1]=m_already[x][y][-zu-2];
					}

			for(x=0;x<=XY_MAX-1;x++)
				for(y=0;y<=XY_MAX-1;y++)
				{
					m_already[x][y][0]=0;
				}
			z--;
		}
	}
	return count;
}

int Tetris::Down()
{
	int i=0;
	bool m=true;

	while(m)
	{
		m=Move(0,0,-1);
		if(m)
		{
			i++;
		}
	}
	
	return i;
}

void Tetris::Rebuild()
{
	ZeroMemory(m_already,sizeof(m_already));
	Reset();
}

int Tetris::GetAlHeight()
{
	int height=10,x,y;

	for(height=10;height>=0;height--)
		for(x=0;x<=XY_MAX-1;x++)
			for(y=0;y<=XY_MAX-1;y++)
				if(m_already[x][y][Z_MAX-height]==1)
				{
					return height;
				}
	return 0;
}

int Tetris::GetPreBlock(PointF **point)
{
	(*point)=(PointF*)malloc((TETRIS_MAX)*sizeof(PointF));

	float xmove=0;
	float ymove=0;

	if((m_preid!=0)&&(m_preid!=4))
	{
		xmove=0.5;
	}

	if(m_preid==4)
	{
		ymove=-0.5;
	}

	int i;
	(*point)[0].X=m_model[m_preid].center.x-X_CENTER+xmove;
	(*point)[0].Y=m_model[m_preid].center.y-Y_CENTER+ymove;
	for(i=0;i<=TETRIS_MAX-2;i++)
	{
		(*point)[i+1].X=m_model[m_preid].other[i].x+xmove;
		(*point)[i+1].Y=-(m_model[m_preid].other[i].y)+ymove;
	}
	return TETRIS_MAX;
}
