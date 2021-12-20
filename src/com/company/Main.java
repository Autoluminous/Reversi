package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

public class Main
{
    public static void main(String[] agrs)
    {
        JPanel p=new JPanel();
        Object[] opt=new Object[]{"PVP","PVE"};
        int k=JOptionPane.showOptionDialog(p,"Choose your gamemode:","GameMode",0,3,null,opt,opt[0]);
        if(k!=JOptionPane.CLOSED_OPTION)
        {
            if(k==JOptionPane.OK_OPTION)
            {
                GameFrame frame=new GameFrame();
                frame.setVisible(true);
            }
            else
            {
                JFrame frame=new JFrame("Selecting Difficulty");
                frame.setBounds(960-200,540-135,400,270);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                JPanel panel=new JPanel();
                JSlider  slider=new JSlider(0,30,15);
                slider.setMajorTickSpacing(5);slider.setMinorTickSpacing(1);
                slider.setPaintTicks(true);
                slider.setPaintLabels(true);
                slider.setOrientation(SwingConstants.VERTICAL);
                Hashtable<Integer,JComponent> hashtable=new Hashtable<>();
                hashtable.put(0,new JLabel("NOOB"));
                hashtable.put(10,new JLabel("NOVICE"));
                hashtable.put(20,new JLabel("APPRENTICE"));
                hashtable.put(30,new JLabel("PRACTICIAN"));
                slider.setLabelTable(hashtable);
                panel.add(slider);
                JButton Bdark=new JButton("Playing as Black");
                Bdark.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int val=slider.getValue();
                        System.out.printf("CHOSEN DARK-VAL:%d\n",val);
                        if(val==0)
                        {
                            System.out.printf("NOOB\n");
                            DarkGameFramePVE0 framePVE=new DarkGameFramePVE0();
                            framePVE.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            framePVE.setVisible(true);
                        }
                        else if(val>0&&val<=10)
                        {
                            System.out.printf("NOVICE\n");
                            DarkGameFramePVE10 framePVE=new DarkGameFramePVE10();
                            framePVE.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            framePVE.setVisible(true);
                        }
                        else if(val>10&&val<=20)
                        {
                            System.out.printf("APPRENTICE\n");
                            DarkGameFramePVE20 framePVE=new DarkGameFramePVE20();
                            framePVE.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            framePVE.setVisible(true);
                        }
                        else if(val>20&&val<=30)
                        {
                            System.out.printf("PRACTICIAN\n");
                            DarkGameFramePVE30 framePVE=new DarkGameFramePVE30();
                            framePVE.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            framePVE.setVisible(true);
                        }
                    }
                });
                JButton BLight=new JButton("Playing as White");
                BLight.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int val=slider.getValue();
                        System.out.printf("CHOSEN LIGHT-VAL:%d\n",val);
                        if(val==0)
                        {
                            System.out.printf("NOOB\n");
                            LightGameFramePVE0 framePVE=new LightGameFramePVE0();
                            framePVE.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            framePVE.setVisible(true);
                        }
                        else if(val>0&&val<=10)
                        {
                            System.out.printf("NOVICE\n");
                            LightGameFramePVE10 framePVE=new LightGameFramePVE10();
                            framePVE.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            framePVE.setVisible(true);
                        }
                        else if(val>10&&val<=20)
                        {
                            System.out.printf("APPRENTICE\n");
                            LightGameFramePVE20 framePVE=new LightGameFramePVE20();
                            framePVE.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            framePVE.setVisible(true);
                        }
                        else if(val>20&&val<=30)
                        {
                            System.out.printf("PRACTICIAN\n");
                            LightGameFramePVE30 framePVE=new LightGameFramePVE30();
                            framePVE.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            framePVE.setVisible(true);
                        }
                    }
                });
                frame.setContentPane(panel);
                frame.add(Bdark);frame.add(BLight);
                frame.setVisible(true);
            }
        }
    }
}
