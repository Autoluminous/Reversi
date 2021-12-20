package com.company;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

public class DarkGameFramePVE0 extends JFrame
{
    int Ppic=1,Epic=-1;
    int now;
    int bcnt=0;
    int[][][] board=new int[100][10][10];
    int[][] moves=new int[100][5];
    boolean[][] can=new boolean[10][10];
    int[] dx={0,-1,0,1,-1,1,-1,0,1};
    int[] dy={0,-1,-1,-1,0,0,1,1,1};
    int[] choice=new int[100];
    JButton[][] button=new JButton[10][10];
    protected Timer timer;
    protected Timer timer2;
    String bgp = new File("").getAbsolutePath() + "\\ReversiMaterials\\icons\\bg.png";
    File bgf=new File(bgp);
    String selefp = new File("").getAbsolutePath() + "\\ReversiMaterials\\icons\\selected.png";
    File selef=new File(selefp);
    String blackfp = new File("").getAbsolutePath() + "\\ReversiMaterials\\icons\\black.png";
    File blackf=new File(blackfp);
    String whitefp = new File("").getAbsolutePath() + "\\ReversiMaterials\\icons\\white.png";
    File whitef=new File(whitefp);
    String chesmovp = new File("").getAbsolutePath() + "\\ReversiMaterials\\musics\\chessmove.wav";
    File chesmov=new File(chesmovp);
    String victoryp = new File("").getAbsolutePath() + "\\ReversiMaterials\\musics\\victory.wav";
    File victory=new File(victoryp);
    String Eselefp = new File("").getAbsolutePath() + "\\ReversiMaterials\\icons\\Eselected.png";
    File Eselef=new File(Eselefp);
    public void Init()
    {
        int i,j;
        for(i=1;i<=8;i++)
        {
            for(j=1;j<=8;j++)
            {
                button[i][j]=new JButton();
            }
        }
        initboard();
    }
    public GridBagConstraints makecon(int gx, int gy, int gw, int gh, double wx, double wy)
    {
        GridBagConstraints c=new GridBagConstraints();
        c.gridx=gx;c.gridy=gy;c.gridwidth=gw;c.gridheight=gh;c.weightx=wx;c.weighty=wy;
        c.fill=GridBagConstraints.BOTH;
        return c;
    }
    JLabel p1=new JLabel("");
    public boolean buildcan(int pic,boolean spe)
    {
        int i,j;
        int[][] tmp=new int[100][5];
        int cnt=0,cnt2=0;
        for(i=1;i<=8;i++)
        {
            for(j=1;j<=8;j++)
            {
                if(board[bcnt][i][j]==pic)
                {
                    cnt++;
                    tmp[cnt][0]=i;
                    tmp[cnt][1]=j;
                }
                if(board[bcnt][i][j]!=pic&&board[bcnt][i][j]!=0)
                {
                    cnt2++;
                }
            }
        }
        if(pic==1)
        {
            p1.setText("   Black:"+cnt+"     White:"+cnt2+"   BLACK Turn");
        }
        else
        {
            p1.setText("   Black:"+cnt2+"     White:"+cnt+"   WHITE Turn");
        }
        for(i=1;i<=8;i++)
        {
            for(j=1;j<=8;j++)
            {
                can[i][j]=false;
            }
        }
        int k;
        boolean hans=false;
        for(k=1;k<=cnt;k++)
        {
            int xx=tmp[k][0],yy=tmp[k][1];
            int op;
            for(op=1;op<=8;op++)
            {
                int x=xx,y=yy;
                boolean flag=false;
                while((x+dx[op])<=8&&(x+dx[op])>=1&&(y+dy[op])<=8&&(y+dy[op])>=1)
                {
                    x+=dx[op];y+=dy[op];
                    if(board[bcnt][x][y]==pic)
                    {
                        break;
                    }
                    if(board[bcnt][x][y]==0)
                    {
                        if(flag)
                        {
                            hans=true;
                            can[x][y]=true;
                        }
                        break;
                    }
                    if(board[bcnt][x][y]!=0&&board[bcnt][x][y]!=pic)
                    {
                        flag=true;
                    }
                }
            }
        }
        if(spe==true)
        {
            return hans;
        }
        if(!hans)
        {
            if(buildcan(-pic,true)==true)
            {
                now=-now;
                buildboard(false);
                return true;
            }
            music(victory);
            ImageIcon black=new ImageIcon(blackf.getPath());
            Image temp3 = black.getImage().getScaledInstance(75, 75, black.getImage().SCALE_DEFAULT);
            black = new ImageIcon(temp3);
            ImageIcon white=new ImageIcon(whitef.getPath());
            Image temp4 = white.getImage().getScaledInstance(75, 75, white.getImage().SCALE_DEFAULT);
            white = new ImageIcon(temp4);
            if(pic==-1)
            {
                int t=cnt2;
                cnt2=cnt;
                cnt=t;
            }
            JPanel p=new JPanel();
            if(cnt>cnt2)
            {
                JOptionPane.showMessageDialog(p,"BLACK wins!","Game Over",JOptionPane.PLAIN_MESSAGE,black);
            }
            else if(cnt==cnt2)
            {
                JOptionPane.showMessageDialog(p,"DRAW!","Game Over",JOptionPane.PLAIN_MESSAGE);
            }
            else if(cnt<cnt2)
            {
                JOptionPane.showMessageDialog(p,"WHITE wins!","Game Over",JOptionPane.PLAIN_MESSAGE,white);
            }
//            initboard();
        }
        return true;
    }
    public boolean[][] EvalBuildcan(int pic,int[][] bb)
    {
        boolean[][] evalcan=new boolean[10][10];
        int i,j;
        int[][] tmp=new int[100][5];
        int cnt=0,cnt2=0;
        for(i=1;i<=8;i++)
        {
            for(j=1;j<=8;j++)
            {
                if(bb[i][j]==pic)
                {
                    cnt++;
                    tmp[cnt][0]=i;
                    tmp[cnt][1]=j;
                }
                if(bb[i][j]!=pic&&bb[i][j]!=0)
                {
                    cnt2++;
                }
            }
        }
        for(i=1;i<=8;i++)
        {
            for(j=1;j<=8;j++)
            {
                evalcan[i][j]=false;
            }
        }
        int k;
        for(k=1;k<=cnt;k++)
        {
            int xx=tmp[k][0],yy=tmp[k][1];
            int op;
            for(op=1;op<=8;op++)
            {
                int x=xx,y=yy;
                boolean flag=false;
                while((x+dx[op])<=8&&(x+dx[op])>=1&&(y+dy[op])<=8&&(y+dy[op])>=1)
                {
                    x+=dx[op];y+=dy[op];
                    if(bb[x][y]==pic)
                    {
                        break;
                    }
                    if(bb[x][y]==0)
                    {
                        if(flag)
                        {
                            evalcan[x][y]=true;
                        }
                        break;
                    }
                    if(bb[x][y]!=0&&bb[x][y]!=pic)
                    {
                        flag=true;
                    }
                }
            }
        }
        return evalcan;
    }
    public void music(File mus)
    {
        try {
            Clip c=AudioSystem.getClip();
            c.open(AudioSystem.getAudioInputStream(mus));
            c.loop(0);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }
    public void play(int inx,int iny,int pic)
    {
        if(!can[inx][iny])
        {
            return;
        }
        bcnt++;
        int i,j;
        for(i=1;i<=8;i++)
        {
            for(j=1;j<=8;j++)
            {
                board[bcnt][i][j]=board[bcnt-1][i][j];
            }
        }
        music(chesmov);
        int op;
        int k;
        boolean hans=false;
        for(op=1;op<=8;op++)
        {
            int x=inx,y=iny;
            boolean flag=false;
            int cnt=0;
            while((x+dx[op])<=8&&(x+dx[op])>=1&&(y+dy[op])<=8&&(y+dy[op])>=1)
            {
                x+=dx[op];y+=dy[op];
                cnt++;
                if(board[bcnt][x][y]==0)
                {
                    break;
                }
                if(board[bcnt][x][y]==pic)
                {
                    if(flag)
                    {
                        hans=true;
                        for(k=1;k<cnt;k++)
                        {
                            board[bcnt][inx+k*dx[op]][iny+k*dy[op]]=pic;
                        }
                    }
                    break;
                }
                if(board[bcnt][x][y]!=0&&board[bcnt][x][y]!=pic)
                {
                    flag=true;
                }
            }
        }
        board[bcnt][inx][iny]=pic;
        choice[bcnt]=pic;
        moves[bcnt][0]=inx;moves[bcnt][1]=iny;
        if(pic==-1)
        {
            now=1;
        }
        else if(pic==1)
        {
            now=-1;
        }
        buildboard(false);
    }
    public int[][] Evalplay(int inx,int iny,int pic,int[][] b)
    {
        int[][] bb=new int[10][10];
        int i,j;
        for(i=1;i<=8;i++)
        {
            for(j=1;j<=8;j++)
            {
                bb[i][j]=b[i][j];
            }
        }
        int op;
        int k;
        boolean hans=false;
        for(op=1;op<=8;op++)
        {
            int x=inx,y=iny;
            boolean flag=false;
            int cnt=0;
            while((x+dx[op])<=8&&(x+dx[op])>=1&&(y+dy[op])<=8&&(y+dy[op])>=1)
            {
                x+=dx[op];y+=dy[op];
                cnt++;
                if(bb[x][y]==0)
                {
                    break;
                }
                if(bb[x][y]==pic)
                {
                    if(flag)
                    {
                        hans=true;
                        for(k=1;k<cnt;k++)
                        {
                            bb[inx+k*dx[op]][iny+k*dy[op]]=pic;
                        }
                    }
                    break;
                }
                if(bb[x][y]!=0&&bb[x][y]!=pic)
                {
                    flag=true;
                }
            }
        }
        bb[inx][iny]=pic;
        return bb;
    }
    int[] alpha=new int[10];
    int[] beta=new int[10];
    public int Evaldfs(int cnt,int[][] bb,int pic)
    {
        if(cnt==4)
        {
            return new Valuation(pic,bb,EvalBuildcan(pic,bb)).eval();
        }
        boolean[][] evalcan=EvalBuildcan(pic,bb);
        int tmp=0;
        int i,j;
        if(pic==Epic)
        {
            tmp=-0x3f3f3f3f;
        }
        else if(pic==Ppic)
        {
            tmp=0x3f3f3f3f;
        }
        boolean flag=false;
        for(i=1;i<=8;i++)
        {
            for(j=1;j<=8;j++)
            {
                if(evalcan[i][j])
                {
                    flag=true;
                    int eval=Evaldfs(cnt+1,Evalplay(i,j,pic,bb),-pic);
                    if(pic==Epic)
                    {
                        if(eval>tmp)
                        {
                            tmp=eval;
                        }
                        if(alpha[cnt]!=-0x3f3f3f3f&&eval>alpha[cnt])
                        {
                            return eval;
                        }
                    }
                    else if(pic==Ppic)
                    {
                        if(eval<tmp)
                        {
                            tmp=eval;
                        }
                        if(beta[cnt]!=0x3f3f3f3f&&eval<beta[cnt])
                        {
                            return eval;
                        }
                    }
                }
            }
        }
        if(pic==Epic)
        {
            alpha[cnt]=Math.min(alpha[cnt],tmp);
        }
        if(pic==Ppic)
        {
            beta[cnt]=Math.max(beta[cnt],tmp);
        }
        if(flag)
        {
            return tmp;
        }
        else
        {
            int sum=0;
            for(i=1;i<=8;i++)
            {
                for(j=1;j<=8;j++)
                {
                    sum+=bb[i][j];
                }
            }
            if(sum*Epic>0)
            {
                System.out.printf("0x3f3f3f3f\n");
                return 0x3f3f3f3f;
            }
            else if(sum*Epic<0)
            {
                System.out.printf("-0x3f3f3f3f\n");
                return -0x3f3f3f3f;
            }
            else
            {
                return 0;
            }
        }
    }
    int Etimer=0;
    int Etot=2;
    int Blink=0;
    int Blinktot=4;
    int evalx=0,evaly=0;
    public void buildboard(boolean enableCheat)
    {
        if(!enableCheat)
        {
            buildcan(now,false);
        }
        ImageIcon bg=new ImageIcon(bgf.getPath());
        Image temp1 = bg.getImage().getScaledInstance(75, 75, bg.getImage().SCALE_DEFAULT);
        bg = new ImageIcon(temp1);
        ImageIcon selected=new ImageIcon(selef.getPath());
        Image temp2 = selected.getImage().getScaledInstance(75, 75, selected.getImage().SCALE_DEFAULT);
        selected = new ImageIcon(temp2);
        ImageIcon black=new ImageIcon(blackf.getPath());
        Image temp3 = black.getImage().getScaledInstance(75, 75, black.getImage().SCALE_DEFAULT);
        black = new ImageIcon(temp3);
        ImageIcon white=new ImageIcon(whitef.getPath());
        Image temp4 = white.getImage().getScaledInstance(75, 75, white.getImage().SCALE_DEFAULT);
        white = new ImageIcon(temp4);
        ImageIcon Esel=new ImageIcon(Eselef.getPath());
        Image temp5 = Esel.getImage().getScaledInstance(75, 75, Esel.getImage().SCALE_DEFAULT);
        Esel = new ImageIcon(temp5);
        int i,j;
        if(now==Ppic)
        {
            boolean ok=false;
            for(i=1;i<=8;i++)
            {
                for(j=1;j<=8;j++)
                {
                    button[i][j].setEnabled(true);
                    button[i][j].setRolloverIcon(null);
                    button[i][j].setPreferredSize(new Dimension(80,80));
                    if(board[bcnt][i][j]==0)
                    {
                        button[i][j].setIcon(bg);
                        if(can[i][j]==true)
                        {
                            ok=true;
                            button[i][j].setRolloverIcon(selected);
                        }
                    }
                    else if(board[bcnt][i][j]==1)
                    {
                        button[i][j].setIcon(black);
                    }
                    else if(board[bcnt][i][j]==-1)
                    {
                        button[i][j].setIcon(white);
                    }
                }
            }
            if(ok==false)
            {
                System.out.printf("Nooooooooo");
                now=-now;
                buildboard(false);
                return;
            }
            for(i=1;i<=8;i++)
            {
                final boolean[] flag = {false};
                for(j=1;j<=8;j++)
                {
                    if(can[i][j]==true)
                    {
                        final int inx=i;final int iny=j;final int pic=now;
                        button[i][j].addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                play(inx,iny,pic);
                                flag[0]=true;
                            }
                        });
                    }
                    if(flag[0]==true)
                    {
                        break;
                    }
                }
                if(flag[0]==true)
                {
                    break;
                }
            }
        }
        else
        {
            for(i=1;i<=8;i++)
            {
                for(j=1;j<=8;j++)
                {
                    button[i][j].setEnabled(false);
                    if(board[bcnt][i][j]==0)
                    {
                        button[i][j].setDisabledIcon(bg);
                    }
                    else if(board[bcnt][i][j]==1)
                    {
                        button[i][j].setDisabledIcon(black);
                    }
                    else if(board[bcnt][i][j]==-1)
                    {
                        button[i][j].setDisabledIcon(white);
                    }
                }
            }
            for(i=0;i<=9;i++)
            {
                alpha[i]=-0x3f3f3f3f;
                beta[i]=0x3f3f3f3f;
            }
            int besteval=0;
            evalx=0;evaly=0;
            for(i=1;i<=8;i++)
            {
                for(j=1;j<=8;j++)
                {
                    if(can[i][j])
                    {
                        evalx=i;evaly=j;
                        break;
                    }
                }
            }
            if(evalx==0&&evaly==0)
            {
                if(buildcan(-now,true))
                {
                    now=-now;
                    buildboard(false);
                    return;
                }
            }
//            for(i=1;i<=8;i++)
//            {
//                for(j=1;j<=8;j++)
//                {
//                    if(can[i][j])
//                    {
//                        int theneval=Evaldfs(1,Evalplay(i,j,now,board[bcnt]),-now);
//                        if(theneval>besteval)
//                        {
//                            besteval=theneval;
//                            evalx=i;evaly=j;
//                        }
//                    }
//                }
//            }
            Etimer=0;Etot=2;
            Blink=0;Blinktot=4;
            int DELAY=1000;
            int finalEvalx = evalx;
            int finalEvaly = evaly;
            System.out.printf("WhiteAI:%d %d\n",evalx,evaly);
            ImageIcon finalSelected = selected;
            ImageIcon finalBg = bg;
            ImageIcon finalEsel = Esel;
            timer=new Timer(300, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(Blink==Blinktot)
                    {
                        button[evalx][evaly].setDisabledIcon(finalBg);
                        timer.stop();
                        play(evalx, evaly,now);
                    }
                    else
                    {
                        if(Blink%2==0)
                        {
                            button[evalx][evaly].setDisabledIcon(finalEsel);
                        }
                        else if(Blink%2==1)
                        {
                            button[evalx][evaly].setDisabledIcon(finalBg);
                        }
                        Blink++;
                    }
                }
            });
            timer.start();
        }
    }
    public void initboard()
    {
        bcnt=0;
        int i,j;
        for(i=1;i<=8;i++)
        {
            for(j=1;j<=8;j++)
            {
                board[bcnt][i][j]=0;
            }
        }
        board[bcnt][4][4]=board[bcnt][5][5]=-1;
        board[bcnt][4][5]=board[bcnt][5][4]=1;
        now=1;
        buildboard(false);
    }
    public void cheat(int x)
    {
        now=x;
        int i,j;
        for(i=1;i<=8;i++)
        {
            for(j=1;j<=8;j++)
            {
                if(board[bcnt][i][j]==0)
                {
                    can[i][j]=true;
                }
            }
        }
        buildboard(true);
    }
    public void save(String path)
    {
        File file=new File(path);
        try {
            if(!file.exists())
            {
                file.createNewFile();
            }
            FileWriter fw=new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            int i,j,k;
            bw.write(Integer.toString(bcnt));bw.newLine();
            for(k=0;k<=bcnt;k++)
            {
                bw.write(Integer.toString(choice[k]));bw.newLine();
                bw.write(Integer.toString(moves[k][0])+" "+Integer.toString(moves[k][1]));bw.newLine();
                for(i=1;i<=8;i++)
                {
                    for(j=1;j<=8;j++)
                    {
                        bw.write(Integer.toString(board[k][i][j])+" ");
                    }
                    bw.newLine();
                    bw.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    int tot=0;
    int fall=0;
    public void display()
    {
        bcnt=fall;
//        buildcan(choice[bcnt],false);
//        if(!can[moves[bcnt][0]][moves[bcnt][1]])
//        {
//            System.out.printf("INVALID MOVE! MoveNo:%d MoveX:%d MoveY:%d\n",bcnt,moves[bcnt][0],moves[bcnt][1]);
//        }
        buildboard(false);
        fall++;
    }
    public void load(String path)
    {
        File file=new File(path);
        if(!file.exists())
        {
            JPanel p=new JPanel();
            JOptionPane.showMessageDialog(p,"未找到文件！","错误 ",0);
        }
        else
        {
            try {
                FileInputStream fis=new FileInputStream(file);
                System.setIn(fis);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Scanner sc=new Scanner(System.in);
            tot=sc.nextInt();
            int i,j,k;
            for(k=0;k<=tot;k++)
            {
                bcnt=k;
                choice[k]=sc.nextInt();
                moves[k][0]=sc.nextInt();moves[k][1]=sc.nextInt();
                for(i=1;i<=8;i++)
                {
                    for(j=1;j<=8;j++)
                    {
                        board[k][i][j]=sc.nextInt();
                    }
                }
            }
            fall=0;
            int DELAY=10000/bcnt;
            timer=new Timer(DELAY, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(fall<=tot)
                    {
                        display();
                    }
                }
            });
            timer.start();
            bcnt=tot;
            buildboard(false);
        }
    }
    public DarkGameFramePVE0()
    {
        Init();
        setTitle("Reversi");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(960-410,540-365,820,730);
        GridBagLayout lay=new GridBagLayout();
        GridBagConstraints con=new GridBagConstraints();
        setLayout(lay);

        JButton Brestart=new JButton("Restart");
        Brestart.setPreferredSize(new Dimension(80,40));
        con=makecon(0,0,2,1,0,0);
        lay.setConstraints(Brestart,con);
        Brestart.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                initboard();
            }
        });

        JButton Bsave=new JButton("Save");
        Bsave.setPreferredSize(new Dimension(80,40));
        con=makecon(2,0,2,1,0,0);
        lay.setConstraints(Bsave,con);
        Bsave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel p=new JPanel();
                String path=JOptionPane.showInputDialog(p,"输入保存文件路径","保存",1);
                save(path);
            }
        });

        JButton Bload=new JButton("Load");
        Bload.setPreferredSize(new Dimension(80,40));
        con=makecon(4,0,2,1,0,0);
        lay.setConstraints(Bload,con);
        Bload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel p=new JPanel();
                String path=JOptionPane.showInputDialog(p,"输入读取文件路径","读取",1);
                load(path);
            }
        });

        JButton BCheater=new JButton("Cheat");
        BCheater.setPreferredSize(new Dimension(80,40));
        con=makecon(6,0,2,1,0,0);
        lay.setConstraints(BCheater,con);
        BCheater.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel p=new JPanel();
                Object[] opt=new Object[]{"黑子","白子"};
                int k=JOptionPane.showOptionDialog(p,"Choose your disk:","Cheater Mode",0,3,null,opt,opt[0]);
                if(k!=JOptionPane.CLOSED_OPTION)
                {
                    if(k==JOptionPane.OK_OPTION)
                    {
                        cheat(1);
                    }
                    else
                    {
                        cheat(-1);
                    }
                }
            }
        });

        JButton BUndo=new JButton("Undo");
        BCheater.setPreferredSize(new Dimension(80,40));
        con=makecon(8,0,2,1,0,0);
        lay.setConstraints(BUndo,con);
        BUndo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(bcnt>0)
                {
                    bcnt-=2;
                    buildboard(false);
                }
            }
        });

//        JLabel p1=new JLabel("");
        p1.setFont(new Font("黑体",Font.BOLD,20));
        p1.setBackground(Color.ORANGE);
        con=makecon(10,0,10,1,0,0);
        lay.setConstraints(p1,con);

        JPanel p2=new JPanel();
        p2.setBackground(Color.BLUE);
        con=makecon(0,1,4,8,0,0);
        lay.setConstraints(p2,con);

        JPanel BoardP=new JPanel();
        BoardP.setLayout(new GridLayout(8,8));
        buildboard(false);
        int i,j;
        for(i=1;i<=8;i++)
        {
            for(j=1;j<=8;j++)
            {
                BoardP.add(button[i][j]);
            }
        }
        con=makecon(4,1,8,8,0,0);
        lay.setConstraints(BoardP, con);

        add(Brestart);
        add(Bsave);
        add(Bload);
        add(BCheater);
        add(BUndo);
        add(p1);
        add(p2);
        add(BoardP);
    }
}
