package com.company;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.util.Date;
import java.util.Scanner;

public class GameFrame extends JFrame
{
    int now;
    int bcnt=0;
    int[][][] board=new int[100][10][10];
    int[][] moves=new int[100][5];
    boolean[][] can=new boolean[10][10];
    int[] dx={0,-1,0,1,-1,1,-1,0,1};
    int[] dy={0,-1,-1,-1,0,0,1,1,1};
    int[] choice=new int[100];
    JButton[][] button=new JButton[10][10];
    boolean[] CheatInf=new boolean[105];
    protected Timer timer;
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
    String BGMp = new File("").getAbsolutePath() + "\\ReversiMaterials\\musics\\BGM.wav";
    File BGMf=new File(BGMp);
    String ErrorLogp = new File("").getAbsolutePath() + "\\ReversiMaterials\\Errorlog.txt";
    File ErrorLog=new File(ErrorLogp);
    String Picp = new File("").getAbsolutePath() + "\\ReversiMaterials\\Pic.png";
    File Picf=new File(Picp);
    String Startp = new File("").getAbsolutePath() + "\\ReversiMaterials\\start.gif";
    File Startf=new File(Startp);
    String Loadingp = new File("").getAbsolutePath() + "\\ReversiMaterials\\loading.gif";
    File Loadingf=new File(Loadingp);
    String BTurnp = new File("").getAbsolutePath() + "\\ReversiMaterials\\blackturn.gif";
    File BTurnf=new File(BTurnp);
    String WTurnp = new File("").getAbsolutePath() + "\\ReversiMaterials\\whiteturn.gif";
    File WTurnf=new File(WTurnp);
    String BWinp = new File("").getAbsolutePath() + "\\ReversiMaterials\\BlackWin.gif";
    File BWinf=new File(BWinp);
    String WWinp = new File("").getAbsolutePath() + "\\ReversiMaterials\\WhiteWin.gif";
    File WWinf=new File(WWinp);
    String Drawp = new File("").getAbsolutePath() + "\\ReversiMaterials\\Draw.gif";
    File Drawf=new File(Drawp);
    String BTurnWp = new File("").getAbsolutePath() + "\\ReversiMaterials\\BTurnW.gif";
    File BTurnWf=new File(BTurnWp);
    String WTurnBp = new File("").getAbsolutePath() + "\\ReversiMaterials\\WTurnB.gif";
    File WTurnBf=new File(WTurnBp);
    String Scorep = new File("").getAbsolutePath() + "\\ReversiMaterials\\icons\\score.png";
    File Scoref=new File(Scorep);
    JLabel p2=new JLabel();
    Clip c;
    boolean nowloading=false;
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
        try {
            c=AudioSystem.getClip();
            c.open(AudioSystem.getAudioInputStream(BGMf));
            c.loop(3);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
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
            c.stop();
            if(cnt>cnt2)
            {
                ImageIcon BWin=new ImageIcon(BWinf.getPath());
                Image temp5 = BWin.getImage().getScaledInstance(160, 640, BWin.getImage().SCALE_DEFAULT);
                BWin = new ImageIcon(temp5);
                p2.setIcon(BWin);
                JOptionPane.showMessageDialog(p,"BLACK wins!","Game Over",JOptionPane.PLAIN_MESSAGE,black);
            }
            else if(cnt==cnt2)
            {
                ImageIcon Draw=new ImageIcon(Drawf.getPath());
                Image temp5 = Draw.getImage().getScaledInstance(160, 640, Draw.getImage().SCALE_DEFAULT);
                Draw = new ImageIcon(temp5);
                p2.setIcon(Draw);
                JOptionPane.showMessageDialog(p,"DRAW!","Game Over",JOptionPane.PLAIN_MESSAGE);
            }
            else if(cnt<cnt2)
            {
                ImageIcon WWin=new ImageIcon(WWinf.getPath());
                Image temp5 = WWin.getImage().getScaledInstance(160, 640, WWin.getImage().SCALE_DEFAULT);
                WWin = new ImageIcon(temp5);
                p2.setIcon(WWin);
                JOptionPane.showMessageDialog(p,"WHITE wins!","Game Over",JOptionPane.PLAIN_MESSAGE,white);
            }
            c.start();
//            initboard();
        }
        return true;
    }
    public boolean getnxt(int pic,int[][] bb)
    {
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
                    if(bb[x][y]==pic)
                    {
                        break;
                    }
                    if(bb[x][y]==0)
                    {
                        if(flag)
                        {
                            hans=true;
                        }
                        break;
                    }
                    if(bb[x][y]!=0&&bb[x][y]!=pic)
                    {
                        flag=true;
                    }
                }
                if(hans)
                {
                    break;
                }
            }
        }
        return hans;
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
        if(!nowloading)
        {
            if(now==1)
            {
                ImageIcon BTurn=new ImageIcon(BTurnf.getPath());
                Image temp5 = BTurn.getImage().getScaledInstance(160, 640, BTurn.getImage().SCALE_DEFAULT);
                BTurn = new ImageIcon(temp5);
                p2.setIcon(BTurn);
            }
            else if(now==-1)
            {
                ImageIcon WTurn=new ImageIcon(WTurnf.getPath());
                Image temp6 = WTurn.getImage().getScaledInstance(160, 640, WTurn.getImage().SCALE_DEFAULT);
                WTurn = new ImageIcon(temp6);
                p2.setIcon(WTurn);
            }
        }
        ImageIcon BTurnW=new ImageIcon(BTurnWf.getPath());
        Image temp5 = BTurnW.getImage().getScaledInstance(75, 75, BTurnW.getImage().SCALE_DEFAULT);
        BTurnW = new ImageIcon(temp5);
        ImageIcon WTurnB=new ImageIcon(WTurnBf.getPath());
        Image temp6 = WTurnB.getImage().getScaledInstance(75, 75, WTurnB.getImage().SCALE_DEFAULT);
        WTurnB = new ImageIcon(temp6);
        int i,j;
        for(i=1;i<=8;i++)
        {
            for(j=1;j<=8;j++)
            {
                button[i][j].setRolloverIcon(null);
                button[i][j].setPreferredSize(new Dimension(80,80));
                if(board[bcnt][i][j]==0)
                {
                    button[i][j].setIcon(bg);
                    if(can[i][j]==true)
                    {
                        button[i][j].setRolloverIcon(selected);
                    }
                }
                else if(board[bcnt][i][j]==1)
                {
                    if(!nowloading&&bcnt>=1&&board[bcnt-1][i][j]==-1)
                    {
                        button[i][j].setIcon(WTurnB);
                    }
                    else
                    {
                        button[i][j].setIcon(black);
                    }
                }
                else if(board[bcnt][i][j]==-1)
                {
                    if(!nowloading&&bcnt>=1&&board[bcnt-1][i][j]==1)
                    {
                        button[i][j].setIcon(BTurnW);
                    }
                    else
                    {
                        button[i][j].setIcon(white);
                    }
                }
            }
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
        for(i=0;i<=70;i++)
        {
            CheatInf[i]=false;
        }
        ImageIcon Start=new ImageIcon(Startf.getPath());
        Image temp1 = Start.getImage().getScaledInstance(160, 640, Start.getImage().SCALE_DEFAULT);
        Start = new ImageIcon(temp1);
        p2.setIcon(Start);
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
        CheatInf[bcnt+1]=true;
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
            bw.write("TotalMoves:"+Integer.toString(bcnt));bw.newLine();
            for(k=0;k<=bcnt;k++)
            {
                bw.write("Choice:"+Integer.toString(choice[k]));bw.newLine();
                bw.write(Integer.toString(moves[k][0])+" "+Integer.toString(moves[k][1]));bw.newLine();
                bw.write("isCheat:");
                if(CheatInf[k])
                {
                    bw.write("True");
                }
                else
                {
                    bw.write("False");
                }
                bw.newLine();
                bw.write("===============");bw.newLine();
                for(i=1;i<=8;i++)
                {
                    for(j=1;j<=8;j++)
                    {
                        bw.write(Integer.toString(board[k][i][j])+" ");
                    }
                    bw.newLine();
                    bw.flush();
                }
                bw.write("===============");bw.newLine();
                bw.write("END");bw.newLine();
                bw.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    int tot=0;
    int fall=0;
    public void display(boolean hasError)
    {
        bcnt=fall;
        now=choice[bcnt];
        buildboard(false);
        fall++;
        if(fall==tot+1)
        {
            now=-now;
            timer.stop();
            nowloading=false;

            JPanel p=new JPanel();
            if(!hasError)
            {
                JOptionPane.showMessageDialog(p,"Loading Complete","Complete ",1);
            }
            else
            {
                JOptionPane.showMessageDialog(p,"AN ERROR OCCURRED, LOADING SUSPENDED!\nCheck Errorlog for further Info","Error! ",0);
            }
            buildboard(false);
        }
    }
    boolean hasError=false;
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
                PrintStream fps=new PrintStream(ErrorLog);
                System.setIn(fis);
                System.setOut(fps);
            } catch (FileNotFoundException e) {
                System.out.printf("Error! File not found!");
                e.printStackTrace();
            }
            Scanner sc=new Scanner(System.in);
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
            try {
                if(!Files.probeContentType(file.toPath()).equals("text/plain"))
                {
                    JPanel p=new JPanel();
                    JOptionPane.showMessageDialog(p,"Unsupported file type!","Error!",0);
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            int i;
            tot=0;
            ImageIcon Loading=new ImageIcon(Loadingf.getPath());
            Image temp1 = Loading.getImage().getScaledInstance(160, 640, Loading.getImage().SCALE_DEFAULT);
            Loading = new ImageIcon(temp1);
            p2.setIcon(Loading);
            try {
                String s=stdin.readLine();
                s+="@@@@@@@@@@@@";
                if(s.substring(0,11).equals("TotalMoves:"))
                {
                    int dig=0;
                    for(i=11;i<s.length();i++)
                    {
                        if(!Character.isDigit(s.charAt(i)))
                        {
                            break;
                        }
                        dig++;
                        tot=tot*10+Character.getNumericValue(s.charAt(i));
                    }
                    if(dig==0||tot<0||tot>60)
                    {
                        System.out.printf("Error! Incorrect Totalmoves!\n");
                    }
                }
                else
                {
                    System.out.printf("Error! Totalmoves not found!\n");
                }
            } catch (IOException e) {
                System.out.printf("Error! No input!\n");
                e.printStackTrace();
            }
            bcnt=tot;
            int k=0;
            for(k=0;k<=tot;k++)
            {
                System.out.printf("Error of #%d:\n",k);
                boolean hasChoice=false,hasPos=false,hasCheatInfo=false,hasBoard=false;
                hasError=false;
                String s="";
                int scnt=0;
                while(!s.equals("END@@@@@@@@@@@@"))
                {
                    try {
                        s=stdin.readLine();
                    } catch (IOException e) {
                        System.out.printf("Error! No input!\n");
                        hasError=true;
                        e.printStackTrace();
                    }
                    scnt++;
                    if(scnt>5)
                    {
                        System.out.printf("Error! Redundant Info\n");
                        hasError=true;
                        break;
                    }
                    if(hasChoice&&hasPos&&hasCheatInfo&&hasBoard&&(!s.equals("END")))
                    {
                        System.out.printf("Error! Redundant Info\n");
                        hasError=true;
                        break;
                    }
                    s+="@@@@@@@@@@@@";
                    if(s.substring(0,7).equals("Choice:"))
                    {
                        hasChoice=true;
                        String stmp=s.substring(7,s.indexOf("@"));
                        if(k!=0&&stmp.equals("1"))
                        {
                            choice[k]=1;
                        }
                        else if(k!=0&&stmp.equals("-1"))
                        {
                            choice[k]=-1;
                        }
                        else if(k==0&&stmp.equals("0"))
                        {
                            choice[k]=0;
                        }
                        else
                        {
                            hasError=true;
                            System.out.printf("Error! Incorrect Choice!\n");
                        }
                    }
                    else if(Character.isDigit(s.charAt(0))&&Character.isDigit(s.charAt(2)))
                    {
                        hasPos=true;
                        int x=Character.getNumericValue(s.charAt(0));
                        int y=Character.getNumericValue(s.charAt(2));
                        moves[k][0]=x;moves[k][1]=y;
                        if(k==0)
                        {
                            if(x!=0||y!=0)
                            {
                                hasError=true;
                                System.out.printf("Error! Incorrect MovePos!\n");
                            }
                        }
                        else
                        {
                            if(x<1||x>8||y<1||y>8)
                            {
                                hasError=true;
                                System.out.printf("Error! Incorrect MovePos!\n");
                            }
                        }
                    }
                    else if(s.equals("isCheat:True@@@@@@@@@@@@"))
                    {
                        hasCheatInfo=true;
                        CheatInf[k]=true;
                    }
                    else if(s.equals("isCheat:False@@@@@@@@@@@@"))
                    {
                        hasCheatInfo=true;
                        CheatInf[k]=false;
                    }
                    else if(s.equals("===============@@@@@@@@@@@@"))
                    {
                        hasBoard=true;
                        String getB="";
                        try {
                            getB=stdin.readLine();
                        } catch (IOException e) {
                            System.out.printf("Error! No input!\n");
                            e.printStackTrace();
                        }
                        int col=0;
                        while(!getB.equals("==============="))
                        {
                            String[] str=getB.split("\\s+");
                            col++;
                            if(str.length==8)
                            {
                                int j=0;
                                for(j=0;j<8;j++)
                                {
                                    if(str[j].equals("1"))
                                    {
                                        board[k][col][j+1]=1;
                                    }
                                    else if(str[j].equals("-1"))
                                    {
                                        board[k][col][j+1]=-1;
                                    }
                                    else if(str[j].equals("0"))
                                    {
                                        board[k][col][j+1]=0;
                                    }
                                    else
                                    {
                                        hasError=true;
                                        System.out.printf("Error! ChessType more than 2!ErrorCode:102\n");
                                    }
                                }
                            }
                            else
                            {
                                hasError=true;
                                System.out.printf("Error! Incorrect Boardsize!ErrorCode:101\n");
                            }
                            try {
                                getB=stdin.readLine();
                            } catch (IOException e) {
                                hasError=true;
                                System.out.printf("Error! No input!\n");
                                e.printStackTrace();
                            }
                        }
                        if(col!=8)
                        {
                            hasError=true;
                            System.out.printf("Error! Incorrect Boardsize!ErrorCode:101\n");
                        }
                    }
                }
                if(!hasChoice)
                {
                    hasError=true;
                    System.out.printf("Error! Choice not found!ErrorCode:103\n");
                }
                if(!hasPos)
                {
                    hasError=true;
                    System.out.printf("Error! MovePos not found!\n");
                }
                if(!hasCheatInfo)
                {
                    hasError=true;
                    System.out.printf("Error! CheatInfo not found!\n");
                }
                if(!hasBoard)
                {
                    hasError=true;
                    System.out.printf("Error! Board not found!\n");
                }
                if(k>0)
                {
                    int[][] tmpBoard=Evalplay(moves[k][0],moves[k][1],choice[k],board[k-1]);
                    for(i=1;i<=8;i++)
                    {
                        int j;
                        for(j=1;j<=8;j++)
                        {
                            if(tmpBoard[i][j]!=board[k][i][j])
                            {
                                hasError=true;
                                System.out.printf("Error! Inconsistency in MovePos and Board!\n");
                                break;
                            }
                        }
                    }
                }
                if(k>0)
                {
                    int nowc=0;
                    if(k==1)
                    {
                        nowc=1;
                    }
                    else
                    {
                        nowc=-choice[k-1];
                    }
                    boolean shouldChange=getnxt(nowc,board[k-1]);
                    if(CheatInf[k]!=true)
                    {
                        if(shouldChange)
                        {
                            if(choice[k]!=nowc)
                            {
                                hasError=true;
                                System.out.printf("Error! Choice Incorrect!\n");
                            }
                        }
                        else
                        {
                            if(choice[k]==nowc)
                            {
                                hasError=true;
                                System.out.printf("Error! Choice Incorrect!\n");
                            }
                        }
                    }
                }
                if(k==0&&!hasError)
                {
                    int[][] tmpB=new int[10][10];
                    for(i=1;i<=8;i++)
                    {
                        int j;
                        for(j=1;j<=8;j++)
                        {
                            tmpB[i][j]=0;
                        }
                    }
                    tmpB[4][4]=tmpB[5][5]=-1;
                    tmpB[4][5]=tmpB[5][4]=1;
                    for(i=1;i<=8;i++)
                    {
                        int j;
                        for(j=1;j<=8;j++)
                        {
                            if(board[k][i][j]!=tmpB[i][j])
                            {
                                hasError=true;
                                System.out.printf("Error! Incorrect Initial Board!\n");
                                break;
                            }
                        }
                    }
                }
                if(!hasError)
                {
                    if(k>0)
                    {
                        if(EvalBuildcan(choice[k],board[k-1])[moves[k][0]][moves[k][1]]==false)
                        {
                            if(CheatInf[k]==true)
                            {
                                System.out.printf("Cheating Move! Code:105\n");
                            }
                            else
                            {
                                System.out.printf("Invalid Move! Code:105\n");
                            }
                        }
                    }
                    System.out.printf("No Error Occurred,#%d Loading Complete\n",k);
                }
                else
                {
                    System.out.printf("Error Occurred in #%d! Loading Suspended\n",k);
                    bcnt=tot=k-1;
                    break;
                }
            }
            if(tot==-1)
            {
                bcnt=0;
                JPanel p=new JPanel();
                JOptionPane.showMessageDialog(p,"An error occurred in initial state\nLoading suspended.","Error! ",0);
                initboard();
                return;
            }
            fall=1;
            int DELAY=10000/bcnt;
            nowloading=true;
            timer=new Timer(DELAY, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(fall<=tot)
                    {
                        display(hasError);
                    }
                }
            });
            timer.start();
        }
    }
    public GameFrame()
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
//                JPanel p=new JPanel();
//                String path=JOptionPane.showInputDialog(p,"输入读取文件路径","读取",1);
                JFileChooser jfc=new JFileChooser();
                jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                jfc.showDialog(new JLabel(), "选择读取文件");
                String path=jfc.getSelectedFile().getAbsolutePath();
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
                    now=choice[bcnt];
                    bcnt--;
                    buildboard(false);
                }
            }
        });

        ImageIcon Score=new ImageIcon(Scoref.getPath());
        Image temp5 = Score.getImage().getScaledInstance(415, 40, Score.getImage().SCALE_DEFAULT);
        Score = new ImageIcon(temp5);
        JLabel tmplabel=new JLabel(Score);
        JPanel pp=new JPanel();
        pp.setBackground(Color.GRAY);
        p1.setFont(new Font("黑体",Font.BOLD,20));
        con=makecon(10,0,10,1,0,0);
        pp.add(p1);
        lay.setConstraints(pp,con);

        ImageIcon Pic=new ImageIcon(Picf.getPath());
        Image temp1 = Pic.getImage().getScaledInstance(160, 640, Pic.getImage().SCALE_DEFAULT);
        Pic = new ImageIcon(temp1);
        p2.setIcon(Pic);
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
        add(pp);
        add(p2);
        add(BoardP);
    }
}
