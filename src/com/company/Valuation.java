package com.company;

public class Valuation
{
    int now;
    int[][] board=new int[10][10];
    boolean[][] can=new boolean[10][10];
    int[][] Vmap=new int[10][10];
    int[] dx={0,-1,0,1,-1,1,-1,0,1};
    int[] dy={0,-1,-1,-1,0,0,1,1,1};
    public Valuation(int now,int[][] board,boolean[][] can)
    {
        this.now=now;
        this.board=board;
        this.can=can;
        setVmap();
    }
    public void setVmap()
    {
        Vmap[1]=new int[]{0,500,-25,10,5,5,10,-25,500,0,0};
        Vmap[2]=new int[]{0,-25,-45,1,1,1,1,-45,-25,0,0};
        Vmap[3]=new int[]{0,10,1,3,2,2,3,1,10,0,0};
        Vmap[4]=new int[]{0,5,1,2,1,1,2,1,5,0,0};
        Vmap[5]=new int[]{0,5,1,2,1,1,2,1,5,0,0};
        Vmap[6]=new int[]{0,10,1,3,2,2,3,1,10,0,0};
        Vmap[7]=new int[]{0,-25,-45,1,1,1,1,-45,-25,0,0};
        Vmap[8]=new int[]{0,500,-25,10,5,5,10,-25,500,0,0};
    }
    public int mapweight()
    {
        int sum=0;
        int i,j;
        for(i=1;i<=8;i++)
        {
            for(j=1;j<=8;j++)
            {
                sum+=board[i][j]*Vmap[i][j];
            }
        }
        return sum*now;
    }
    public int moveweight()
    {
        int mov=0;
        int i,j;
        for(i=1;i<=8;i++)
        {
            for(j=1;j<=8;j++)
            {
                if(can[i][j])
                {
                    mov++;
                }
            }
        }
        return mov;
    }
    public int stableweight()
    {
        int stable=0;
        int i,j;
        for(i=1;i<=8;i++)
        {
            for(j=1;j<=8;j++)
            {
                if(board[i][j]==now)
                {
                    int op;
                    boolean flag=true;
                    for(op=1;op<=8;op++)
                    {
                        if(flag==false)
                        {
                            break;
                        }
                        int x=i,y=j;
                        while((x+dx[op])<=8&&(x+dx[op])>=1&&(y+dy[op])<=8&&(y+dy[op])>=1)
                        {
                            x+=dx[op];y+=dy[op];
                            if(board[x][y]==0)
                            {
                                flag=false;
                                break;
                            }
                        }
                    }
                    if(flag)
                    {
                        stable++;
                    }
                }
            }
        }
        return stable;
    }
    public int eval()
    {
        return mapweight()+15*moveweight()+10*stableweight();
    }
}
