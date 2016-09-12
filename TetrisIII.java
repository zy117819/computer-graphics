package ComputerGraphics.Assignment3;

//third version of tetris game with enhanced feature

import javax.swing.*;  
import java.awt.event.*;  
import java.awt.*;
import java.util.Random;

public class TetrisIII extends JFrame
{
    public static void main(String[] args)
    {
        TetrisIII demo = new TetrisIII();
        demo.setVisible(true);
    }

    double speedFactor = 0.1; //init. speed factor of game
    int row = 20;             //init. rows required for each level
    int scoreFactor = 1;      //init. score factor
    int shape = 7;            //init. total shapes of blocks

    public boolean pauseState = true;
    
    final Tetrisblock aBlock = new Tetrisblock();
    final Timer timer = new Timer(400, aBlock.new TimerListener());

    final Tetrisblock1 bBlock = new Tetrisblock1();
    final Timer timer1 = new Timer(400, bBlock.new TimerListener());

    final Tetrisblock2 cBlock = new Tetrisblock2();
    final Timer timer2 = new Timer(400, cBlock.new TimerListener());

    //initial pause related params
    int rightPauseBorder = 215;
    int rightPauseBorder1 = 415;
    int bottomPauseBorder = 413;
    int bottomPauseBorder2 = 610;

    int labelX = 70;
    int labelY = 150;
    int labelW = 100;
    int labelH = 100;
    int labelS = 30;

    int labelX1 = 170;
    int labelY1 = 150;
    int labelW1 = 100;
    int labelH1 = 100;
    int labelS1 = 30;

    int labelX2 = 70;
    int labelY2 = 250;
    int labelW2 = 100;
    int labelH2 = 100;
    int labelS2 = 30;

    int mode = 0;  //flag of game mode

    public TetrisIII(){   
        final JFrame frame = new JFrame("TetrisIII");

        final JMenuBar menu = new JMenuBar();
        frame.setJMenuBar(menu);

        final JMenu modeMenu = new JMenu("Game Mode");
        JMenuItem tentwentyModeItem = new JMenuItem("10X20");
        JMenuItem twentytwentyModeItem = new JMenuItem("20X20");
        JMenuItem tenthirtyModeItem = new JMenuItem("10X30");
        modeMenu.add(tentwentyModeItem);    
        modeMenu.add(twentytwentyModeItem); 
        modeMenu.add(tenthirtyModeItem);    
        modeMenu.setToolTipText("Select one mode to start");
        menu.add(modeMenu);

        tentwentyModeItem.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    frame.remove(bBlock);
                    timer1.stop();
                    frame.remove(cBlock);
                    timer2.stop();
                    frame.add(aBlock);   
                    timer.start();
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setBounds(100,100,400,500);
                    frame.setVisible(true);
                    frame.setResizable(false);
                    mode = 0;
                    modeMenu.disable();
                }
            });
        twentytwentyModeItem.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    frame.remove(aBlock);
                    timer.stop();
                    frame.remove(cBlock);
                    timer2.stop();
                    frame.add(bBlock);   
                    timer1.start();
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setBounds(100,100,600,500);
                    frame.setVisible(true);
                    frame.setResizable(false);
                    mode = 1;
                    modeMenu.disable();
                }
            });
        tenthirtyModeItem.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    frame.remove(bBlock);
                    timer1.stop();
                    frame.remove(aBlock);
                    timer.stop();
                    frame.add(cBlock);   
                    timer2.start();
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setBounds(100,100,400,700);
                    frame.setVisible(true);
                    frame.setResizable(false);
                    mode = 2;
                    modeMenu.disable();
                }
            });

        JMenu setMenu = new JMenu("Settings");
        JMenu speedMenu = new JMenu("Speed Factor");
        JMenuItem pointOneItem = new JMenuItem("0.1");
        JMenuItem pointTwoItem = new JMenuItem("0.2");
        JMenuItem pointThreeItem = new JMenuItem("0.3");
        JMenuItem pointFourItem = new JMenuItem("0.4");
        JMenuItem pointFiveItem = new JMenuItem("0.5");
        JMenuItem pointSixItem = new JMenuItem("0.6");
        JMenuItem pointSevenItem = new JMenuItem("0.7");
        JMenuItem pointEightItem = new JMenuItem("0.8");
        JMenuItem pointNineItem = new JMenuItem("0.9");
        JMenuItem pointTenItem = new JMenuItem("1.0");
        speedMenu.add(pointOneItem);
        speedMenu.add(pointTwoItem);
        speedMenu.add(pointThreeItem);
        speedMenu.add(pointFourItem);
        speedMenu.add(pointFiveItem);
        speedMenu.add(pointSixItem);
        speedMenu.add(pointSevenItem);
        speedMenu.add(pointEightItem);
        speedMenu.add(pointNineItem);
        speedMenu.add(pointTenItem);
        setMenu.add(speedMenu);
        speedMenu.setToolTipText("To set the speed factor of game");
        setMenu.setToolTipText("Adjustable game parameters for user");

        pointOneItem.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    speedFactor = 0.1;
                }
            });
        pointTwoItem.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    speedFactor = 0.2;
                }
            });
        pointThreeItem.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    speedFactor = 0.3;
                }
            });
        pointFourItem.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    speedFactor = 0.4;
                }
            });
        pointFiveItem.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    speedFactor = 0.5;
                }
            });
        pointSixItem.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    speedFactor = 0.6;
                }
            });
        pointSevenItem.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    speedFactor = 0.7;
                }
            });
        pointEightItem.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    speedFactor = 0.8;
                }
            });
        pointNineItem.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    speedFactor = 0.9;
                }
            });
        pointTenItem.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    speedFactor = 1;
                }
            });

        JMenu rowMenu = new JMenu("Row # for each level");    
        JMenuItem twentyItem = new JMenuItem("20 lines");
        JMenuItem thirtyItem = new JMenuItem("30 lines");
        JMenuItem fortyItem = new JMenuItem("40 lines");
        JMenuItem fiftyItem = new JMenuItem("50 lines");
        rowMenu.add(twentyItem);
        rowMenu.add(thirtyItem);
        rowMenu.add(fortyItem);
        rowMenu.add(fiftyItem);
        setMenu.add(rowMenu);
        rowMenu.setToolTipText("To set row number required for each level");

        twentyItem.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    row = 20;
                }
            });
        thirtyItem.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    row = 30;
                }
            });
        fortyItem.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    row = 40;
                }
            });
        fiftyItem.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    row = 50;
                }
            });

        JMenu scoreMenu = new JMenu("Scoring factor");    
        JMenuItem oneItem = new JMenuItem("1 X bonus");
        JMenuItem twoItem = new JMenuItem("2 X bonus");
        JMenuItem threeItem = new JMenuItem("3 X bonus");
        JMenuItem fourItem = new JMenuItem("4 X bonus");
        JMenuItem fiveItem = new JMenuItem("5 X bonus");
        JMenuItem sixItem = new JMenuItem("6 X bonus");
        JMenuItem sevenItem = new JMenuItem("7 X bonus");
        JMenuItem eightItem = new JMenuItem("8 X bonus");
        JMenuItem nineItem = new JMenuItem("9 X bonus");
        JMenuItem tenItem = new JMenuItem("10 X bonus");
        scoreMenu.add(oneItem);
        scoreMenu.add(twoItem);
        scoreMenu.add(threeItem);
        scoreMenu.add(fourItem);
        scoreMenu.add(fiveItem);
        scoreMenu.add(sixItem);
        scoreMenu.add(sevenItem);
        scoreMenu.add(eightItem);
        scoreMenu.add(nineItem);
        scoreMenu.add(tenItem);
        setMenu.add(scoreMenu);
        scoreMenu.setToolTipText("To set score factor");

        oneItem.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    scoreFactor = 1;
                }
            });
        twoItem.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    scoreFactor = 2;
                }
            });
        threeItem.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    scoreFactor = 3;
                }
            });
        fourItem.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    scoreFactor = 4;
                }
            });
        fiveItem.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    scoreFactor = 5;
                }
            });
        sixItem.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    scoreFactor = 6;
                }
            });
        sevenItem.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    scoreFactor = 7;
                }
            });
        eightItem.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    scoreFactor = 8;
                }
            });
        nineItem.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    scoreFactor = 9;
                }
            });
        tenItem.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    scoreFactor = 10;
                }
            });
        menu.add(setMenu);

        JMenu resizeMenu = new JMenu("Resize");
        JMenuItem sizeOriginalItem = new JMenuItem("original size");
        JMenuItem sizeOneItem = new JMenuItem("1.2 X size");
        JMenuItem sizeTwoItem = new JMenuItem("1.5 X size");
        JMenuItem sizeThreeItem = new JMenuItem("1.8 X size");
        resizeMenu.add(sizeOriginalItem);
        resizeMenu.add(sizeOneItem);
        resizeMenu.add(sizeTwoItem);
        resizeMenu.add(sizeThreeItem);
        resizeMenu.setToolTipText("Resize for a better view");

        final JButton originQuitButton = new JButton("QUIT");
        final JButton originQuitButton1 = new JButton("QUIT");
        final JButton originQuitButton2 = new JButton("QUIT");

        sizeOriginalItem.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    if (mode == 0)
                    {
                        frame.setBounds(100,100,400,500);
                        Rectangle d = frame.getBounds();
                        originQuitButton.setBounds(d.width*13/20, d.width*7/8, d.width/4, d.width/10);
                        originQuitButton.setFont(new Font("Serif", Font.PLAIN, d.width/20));
                        originQuitButton.setToolTipText("Click to quit");
                        rightPauseBorder = 215;
                        bottomPauseBorder = 413;
                        labelX = 70;
                        labelY = 150;
                        labelW = 100;
                        labelH = 100;
                        labelS = 30;
                    }
                    else if (mode == 1)
                    {
                        frame.setBounds(100,100,600,500);
                        Rectangle d1 = frame.getBounds();
                        originQuitButton1.setBounds(d1.width*23/30, d1.width*7/12, d1.width/6, d1.width/15);
                        originQuitButton1.setFont(new Font("Serif", Font.PLAIN, d1.width/30));
                        originQuitButton1.setToolTipText("Click to quit");
                        rightPauseBorder1 = 415;
                        bottomPauseBorder = 413;
                        labelX1 = 170;
                        labelY1 = 150;
                        labelW1 = 100;
                        labelH1 = 100;
                        labelS1 = 30;
                    }
                    else
                    {
                        frame.setBounds(100,100,400,700);
                        Rectangle d = frame.getBounds();
                        originQuitButton2.setBounds(d.width*13/20, d.width*11/8, d.width/4, d.width/10);
                        originQuitButton2.setFont(new Font("Serif", Font.PLAIN, d.width/20));
                        originQuitButton2.setToolTipText("Click to quit");
                        rightPauseBorder = 215;
                        bottomPauseBorder2 = 610;
                        labelX2 = 70;
                        labelY2 = 250;
                        labelW2 = 100;
                        labelH2 = 100;
                        labelS2 = 30;
                    }
                }
            });

        sizeOneItem.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    if (mode == 0)
                    {
                        frame.setBounds(100,100,480,600);
                        Rectangle d = frame.getBounds();
                        originQuitButton.setBounds(d.width*13/20, d.width*7/8, d.width/4, d.width/10);
                        originQuitButton.setFont(new Font("Serif", Font.PLAIN, d.width/20));
                        originQuitButton.setToolTipText("Click to quit");
                        rightPauseBorder = 260;
                        bottomPauseBorder = 496;
                        labelX = 84;
                        labelY = 180;
                        labelW = 120;
                        labelH = 120;
                        labelS = 36;
                    }
                    else if (mode == 1)
                    {
                        frame.setBounds(100,100,720,600);
                        Rectangle d1 = frame.getBounds();
                        originQuitButton1.setBounds(d1.width*23/30, d1.width*7/12, d1.width/6, d1.width/15);
                        originQuitButton1.setFont(new Font("Serif", Font.PLAIN, d1.width/30));
                        originQuitButton1.setToolTipText("Click to quit");
                        rightPauseBorder1 = 500;
                        bottomPauseBorder = 496;
                        labelX1 = 184;
                        labelY1 = 180;
                        labelW1 = 120;
                        labelH1 = 120;
                        labelS1 = 36;
                    }
                    else
                    {
                        frame.setBounds(100,100,480,840);
                        Rectangle d = frame.getBounds();
                        originQuitButton2.setBounds(d.width*13/20, d.width*11/8, d.width/4, d.width/10);
                        originQuitButton2.setFont(new Font("Serif", Font.PLAIN, d.width/20));
                        originQuitButton2.setToolTipText("Click to quit");
                        rightPauseBorder = 260;
                        bottomPauseBorder2 = 732;
                        labelX2 = 84;
                        labelY2 = 400;
                        labelW2 = 120;
                        labelH2 = 120;
                        labelS2 = 36;
                    }
                }
            });

        sizeTwoItem.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    if (mode == 0)
                    {
                        frame.setBounds(100,100,600,750);
                        Rectangle d = frame.getBounds();
                        originQuitButton.setBounds(d.width*13/20, d.width*7/8, d.width/4, d.width/10);
                        originQuitButton.setFont(new Font("Serif", Font.PLAIN, d.width/20));
                        originQuitButton.setToolTipText("Click to quit");
                        rightPauseBorder = 325;
                        bottomPauseBorder = 620;
                        labelX = 105;
                        labelY = 225;
                        labelW = 150;
                        labelH = 150;
                        labelS = 45;
                    }
                    else if (mode == 1)
                    {
                        frame.setBounds(100,100,900,750);
                        Rectangle d1 = frame.getBounds();
                        originQuitButton1.setBounds(d1.width*23/30, d1.width*7/12, d1.width/6, d1.width/15);
                        originQuitButton1.setFont(new Font("Serif", Font.PLAIN, d1.width/30));
                        originQuitButton1.setToolTipText("Click to quit");
                        rightPauseBorder1 = 625;
                        bottomPauseBorder = 620;
                        labelX1 = 230;
                        labelY1 = 225;
                        labelW1 = 150;
                        labelH1 = 150;
                        labelS1 = 45;
                    }
                    else 
                    {
                        frame.setBounds(100,100,600,1050);
                        Rectangle d = frame.getBounds();
                        originQuitButton2.setBounds(d.width*13/20, d.width*11/8, d.width/4, d.width/10);
                        originQuitButton2.setFont(new Font("Serif", Font.PLAIN, d.width/20));
                        originQuitButton2.setToolTipText("Click to quit");
                        rightPauseBorder = 325;
                        bottomPauseBorder2 = 915;
                        labelX2 = 105;
                        labelY2 = 375;
                        labelW2 = 150;
                        labelH2 = 150;
                        labelS2 = 45;
                    }
                }
            });

        sizeThreeItem.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    if (mode == 0)
                    {
                        frame.setBounds(100,100,720,900);
                        Rectangle d = frame.getBounds();
                        originQuitButton.setBounds(d.width*13/20, d.width*7/8, d.width/4, d.width/10);
                        originQuitButton.setFont(new Font("Serif", Font.PLAIN, d.width/20));
                        originQuitButton.setToolTipText("Click to quit");
                        rightPauseBorder = 390;
                        bottomPauseBorder = 744;
                        labelX = 126;
                        labelY = 270;
                        labelW = 180;
                        labelH = 180;
                        labelS = 48;
                    }
                    else if (mode == 1)
                    {
                        frame.setBounds(100,100,1080,900);
                        Rectangle d1 = frame.getBounds();
                        originQuitButton1.setBounds(d1.width*23/30, d1.width*7/12, d1.width/6, d1.width/15);
                        originQuitButton1.setFont(new Font("Serif", Font.PLAIN, d1.width/30));
                        originQuitButton1.setToolTipText("Click to quit");
                        rightPauseBorder1 = 750;
                        bottomPauseBorder = 744;
                        labelX1 = 260;
                        labelY1 = 270;
                        labelW1 = 180;
                        labelH1 = 180;
                        labelS1 = 48;
                    }
                    else
                    {
                        frame.setBounds(100,100,720,1260);
                        Rectangle d = frame.getBounds();
                        originQuitButton2.setBounds(d.width*13/20, d.width*11/8, d.width/4, d.width/10);
                        originQuitButton2.setFont(new Font("Serif", Font.PLAIN, d.width/20));
                        originQuitButton2.setToolTipText("Click to quit");
                        rightPauseBorder = 390;
                        bottomPauseBorder2 = 1098;
                        labelX2 = 126;
                        labelY2 = 450;
                        labelW2 = 180;
                        labelH2 = 180;
                        labelS2 = 48;
                    }
                }
            });

        originQuitButton.setBounds(260, 350, 100, 40);
        originQuitButton.setFont(new Font("Serif", Font.PLAIN, 20));
        originQuitButton.setToolTipText("Click to quit");

        originQuitButton.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    if (e.getActionCommand().equals("QUIT"))
                        System.exit(0);
                }
            });

        aBlock.setLayout(null);
        aBlock.add(originQuitButton);

        originQuitButton1.setBounds(460, 350, 100, 40);
        originQuitButton1.setFont(new Font("Serif", Font.PLAIN, 20));
        originQuitButton1.setToolTipText("Click to quit");

        originQuitButton1.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    if (e.getActionCommand().equals("QUIT"))
                        System.exit(0);
                }
            });

        bBlock.setLayout(null);
        bBlock.add(originQuitButton1);

        originQuitButton2.setBounds(260, 550, 100, 40);
        originQuitButton2.setFont(new Font("Serif", Font.PLAIN, 20));
        originQuitButton2.setToolTipText("Click to quit");

        originQuitButton2.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    if (e.getActionCommand().equals("QUIT"))
                        System.exit(0);
                }
            });

        cBlock.setLayout(null);
        cBlock.add(originQuitButton2);

        menu.add(resizeMenu);

        JMenu newShapesMenu = new JMenu("New Shapes");
        JMenuItem extShape0 = new JMenuItem("No new shapes");
        JMenuItem extShape1 = new JMenuItem("Up to 1 square");
        JMenuItem extShape2 = new JMenuItem("Up to 2 squares");
        JMenuItem extShape3 = new JMenuItem("Up to 3 squares");
        newShapesMenu.add(extShape0);
        newShapesMenu.add(extShape1);
        newShapesMenu.add(extShape2);
        newShapesMenu.add(extShape3);
        newShapesMenu.setToolTipText("Add more shapes for fun");
        extShape0.setToolTipText("No new shapes added");
        extShape1.setToolTipText("New shapes with 1 square added");
        extShape2.setToolTipText("New shapes with 1 or 2 squares added");
        extShape3.setToolTipText("New shapes with 1 or 2 or 3 squares added");

        menu.add(newShapesMenu);

        extShape0.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    shape = 7;
                }
            });

        extShape1.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    shape = 8;
                }
            });

        extShape2.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    shape = 10;
                }
            });

        extShape3.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    shape = 16;
                }
            });

        //mouselistener to perform left/right
        aBlock.addMouseListener(new MouseAdapter()
            {  
                public void mousePressed(MouseEvent e){  
                    if (!pauseState)
                    {
                        if (e.getButton() == java.awt.event.MouseEvent.BUTTON1) //left click
                        {
                            aBlock.left();
                        }
                        else if (e.getButton() == java.awt.event.MouseEvent.BUTTON3) //right click
                        {
                            aBlock.right();  
                        }
                    }
                }  
            });

        final JLabel pauseLabel = new JLabel("PAUSE", SwingConstants.CENTER);
        aBlock.add(pauseLabel);
        pauseLabel.setVisible(true);
        //mouselistener to determine the pause state
        aBlock.addMouseMotionListener(new MouseAdapter()
            {
                public void mouseMoved(MouseEvent e)
                {
                    int xP = e.getX();
                    int yP = e.getY();
                    if ((xP >= 20 && xP <= rightPauseBorder) && yP <= bottomPauseBorder)
                    {
                        timer.stop();
                        pauseLabel.setFont(new Font("Serif", Font.PLAIN, labelS));
                        pauseLabel.setBounds(labelX, labelY, labelW, labelH);
                        pauseLabel.setVisible(true);
                        pauseState = true;
                    }
                    else 
                    {
                        timer.start(); 
                        pauseLabel.setVisible(false);
                        pauseState = false;
                    }
                }
            });       
        //mousewheellistener to perform turn/cturn
        aBlock.addMouseWheelListener(new MouseAdapter() 
            {
                public void mouseWheelMoved(MouseWheelEvent e) 
                {
                    int notches = e.getWheelRotation();
                    if (!pauseState){
                        if (notches < 0) aBlock.turn(); //forward scroll
                        else aBlock.cturn();} //backward scroll
                }
            });

        //mouselistener to perform left/right/pause
        bBlock.addMouseListener(new MouseAdapter()
            {  
                public void mousePressed(MouseEvent e){  
                    if (!pauseState)
                    {
                        if (e.getButton() == java.awt.event.MouseEvent.BUTTON1) //left click
                        {
                            bBlock.left();
                        }
                        else if (e.getButton() == java.awt.event.MouseEvent.BUTTON3) //right click
                        {
                            bBlock.right();  
                        }
                    }
                }  
            });

        final JLabel pauseLabel1 = new JLabel("PAUSE", SwingConstants.CENTER);
        bBlock.add(pauseLabel1);
        pauseLabel1.setVisible(true);
        //mouselistener to determine the pause state
        bBlock.addMouseMotionListener(new MouseAdapter()
            {
                public void mouseMoved(MouseEvent e)
                {
                    int xP = e.getX();
                    int yP = e.getY();
                    if ((xP >= 20 && xP <= rightPauseBorder1) && yP <= bottomPauseBorder)
                    {
                        timer1.stop();
                        pauseLabel1.setFont(new Font("Serif", Font.PLAIN, labelS1));
                        pauseLabel1.setBounds(labelX1, labelY1, labelW1, labelH1);
                        pauseLabel1.setVisible(true);
                        pauseState = true;
                    }
                    else
                    {
                        timer1.start(); 
                        pauseLabel1.setVisible(false);
                        pauseState = false;
                    }
                }
            });        
        //mousewheellistener to perform turn/cturn
        bBlock.addMouseWheelListener(new MouseAdapter() 
            {
                public void mouseWheelMoved(MouseWheelEvent e) 
                {
                    if (!pauseState){
                        int notches = e.getWheelRotation();
                        if (notches < 0) bBlock.turn(); //forward scroll
                        else bBlock.cturn();} //backward scroll
                }
            });

        //mouselistener to perform left/right
        cBlock.addMouseListener(new MouseAdapter()
            {  
                public void mousePressed(MouseEvent e){  
                    if (!pauseState)
                    {
                        if (e.getButton() == java.awt.event.MouseEvent.BUTTON1) //left click
                        {
                            cBlock.left();
                        }
                        else if (e.getButton() == java.awt.event.MouseEvent.BUTTON3) //right click
                        {
                            cBlock.right();  
                        }
                    }
                }  
            });

        final JLabel pauseLabel2 = new JLabel("PAUSE", SwingConstants.CENTER);
        cBlock.add(pauseLabel2);
        pauseLabel2.setVisible(true);
        //mouselistener to determine the pause state
        cBlock.addMouseMotionListener(new MouseAdapter()
            {
                public void mouseMoved(MouseEvent e)
                {
                    int xP = e.getX();
                    int yP = e.getY();
                    if ((xP >= 20 && xP <= rightPauseBorder) && yP <= bottomPauseBorder2)
                    {
                        timer2.stop();
                        pauseLabel2.setFont(new Font("Serif", Font.PLAIN, labelS2));
                        pauseLabel2.setBounds(labelX2, labelY2, labelW2, labelH2);
                        pauseLabel2.setVisible(true);
                        pauseState = true;
                    }
                    else 
                    {
                        timer2.start(); 
                        pauseLabel2.setVisible(false);
                        pauseState = false;
                    }
                }
            });       
        //mousewheellistener to perform turn/cturn
        cBlock.addMouseWheelListener(new MouseAdapter() 
            {
                public void mouseWheelMoved(MouseWheelEvent e) 
                {
                    int notches = e.getWheelRotation();
                    if (!pauseState){
                        if (notches < 0) cBlock.turn(); //forward scroll
                        else cBlock.cturn();} //backward scroll
                }
            });

        frame.setLayout(new GridLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100,100,400,500);
        frame.setVisible(true);
        frame.setResizable(false);
        JOptionPane.showMessageDialog(null,"Select a game mode to start with", "Alert", JOptionPane.PLAIN_MESSAGE);
        
    }

    // create a 10X20 Tetrisblock class
    class Tetrisblock extends JPanel
    {
        Random ran = new Random();
        public int blockType; //block type
        public int turnState; //block status
        public int x; //initial x-coordinate of block
        public int y; //initial y-coordinate of block
        public int nextb = ran.nextInt(shape); //next block type
        public int nextt = ran.nextInt(4); //next block status
        private int i = 0;
        private int j = 0;
        private boolean statusFlag = false;
        int[][] map = new int[13][27];

        public int score = 0; //init. score
        public int line = 0;  //init. line deleted at current level
        public int level = 1; //init. level
        public int showline = 0; //init. total line deleted

        Tetrisblock()
        {
            newblockparam();
            newmapparam();
            setboundary();
        }

        //3-D array to display different shapes. 1st d: block type, 2nd d: turn state, 3rd d: drawing param
        private final int shapes[][][] = new int[][][] 
            {
                // line-shape
                { 
                    { 0, 0, 0, 0, 
                        0, 0, 0, 0, 
                        0, 0, 0, 0, 
                        1, 1, 1, 1 },
                    { 0, 1, 0, 0,
                        0, 1, 0, 0, 
                        0, 1, 0, 0, 
                        0, 1, 0, 0 },
                    { 0, 0, 0, 0, 
                        0, 0, 0, 0, 
                        0, 0, 0, 0, 
                        1, 1, 1, 1 },
                    { 0, 1, 0, 0, 
                        0, 1, 0, 0, 
                        0, 1, 0, 0, 
                        0, 1, 0, 0 }
                },
                // s-shape
                { 
                    { 0, 0, 0, 0, 
                        0, 0, 0, 0, 
                        0, 1, 1, 0, 
                        1, 1, 0, 0 },
                    { 0, 0, 0, 0, 
                        1, 0, 0, 0, 
                        1, 1, 0, 0, 
                        0, 1, 0, 0 },
                    { 0, 0, 0, 0, 
                        0, 0, 0, 0,
                        0, 1, 1, 0, 
                        1, 1, 0, 0 },
                    { 0, 0, 0, 0,
                        1, 0, 0, 0, 
                        1, 1, 0, 0,
                        0, 1, 0, 0 } 
                },
                // z-shape
                { 
                    { 0, 0, 0, 0, 
                        0, 0, 0, 0, 
                        1, 1, 0, 0, 
                        0, 1, 1, 0 },
                    { 0, 0, 0, 0, 
                        0, 1, 0, 0, 
                        1, 1, 0, 0, 
                        1, 0, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        1, 1, 0, 0, 
                        0, 1, 1, 0 },
                    { 0, 0, 0, 0,
                        0, 1, 0, 0, 
                        1, 1, 0, 0, 
                        1, 0, 0, 0 } 
                },
                // mirror-l-shape
                { 
                    { 0, 0, 0, 0, 
                        0, 1, 0, 0, 
                        0, 1, 0, 0, 
                        1, 1, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        1, 0, 0, 0, 
                        1, 1, 1, 0 },
                    { 0, 0, 0, 0,
                        1, 1, 0, 0,
                        1, 0, 0, 0, 
                        1, 0, 0, 0 },
                    { 0, 0, 0, 0, 
                        0, 0, 0, 0, 
                        1, 1, 1, 0, 
                        0, 0, 1, 0 } 
                },
                // square-shape
                { 
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        1, 1, 0, 0, 
                        1, 1, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        1, 1, 0, 0, 
                        1, 1, 0, 0 },
                    { 0, 0, 0, 0, 
                        0, 0, 0, 0, 
                        1, 1, 0, 0, 
                        1, 1, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        1, 1, 0, 0, 
                        1, 1, 0, 0 } 
                },
                // l-shape
                { 
                    { 0, 0, 0, 0,
                        1, 0, 0, 0, 
                        1, 0, 0, 0,
                        1, 1, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        1, 1, 1, 0,
                        1, 0, 0, 0 },
                    { 0, 0, 0, 0,
                        1, 1, 0, 0, 
                        0, 1, 0, 0, 
                        0, 1, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        0, 0, 1, 0, 
                        1, 1, 1, 0 } 
                },
                // t-shape
                { 
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        0, 1, 0, 0, 
                        1, 1, 1, 0 },
                    { 0, 0, 0, 0,
                        0, 1, 0, 0, 
                        1, 1, 0, 0, 
                        0, 1, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0,
                        1, 1, 1, 0,
                        0, 1, 0, 0 },
                    { 0, 0, 0, 0, 
                        0, 1, 0, 0,
                        0, 1, 1, 0, 
                        0, 1, 0, 0 } 
                },
                //new-shape1
                { 
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        0, 0, 0, 0, 
                        1, 0, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        0, 0, 0, 0, 
                        1, 0, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0,
                        0, 0, 0, 0,
                        1, 0, 0, 0 },
                    { 0, 0, 0, 0, 
                        0, 0, 0, 0,
                        0, 0, 0, 0, 
                        1, 0, 0, 0 } 
                },

                //new-shape2
                { 
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        0, 1, 0, 0, 
                        1, 0, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        1, 0, 0, 0, 
                        0, 1, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0,
                        0, 1, 0, 0,
                        1, 0, 0, 0 },
                    { 0, 0, 0, 0, 
                        0, 0, 0, 0,
                        1, 0, 0, 0, 
                        0, 1, 0, 0 } 
                },

                //new-shape3
                { 
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        0, 1, 0, 0, 
                        0, 1, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        0, 0, 0, 0, 
                        1, 1, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0,
                        0, 1, 0, 0,
                        0, 1, 0, 0 },
                    { 0, 0, 0, 0, 
                        0, 0, 0, 0,
                        0, 0, 0, 0, 
                        1, 1, 0, 0 } 
                },

                //new-shape4
                { 
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        0, 1, 0, 0, 
                        1, 1, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        1, 0, 0, 0, 
                        1, 1, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0,
                        1, 1, 0, 0,
                        1, 0, 0, 0 },
                    { 0, 0, 0, 0, 
                        0, 0, 0, 0,
                        1, 1, 0, 0, 
                        0, 1, 0, 0 } 
                },
                //new-shape5
                { 
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        1, 0, 0, 0, 
                        0, 1, 1, 0 },
                    { 0, 0, 0, 0,
                        0, 1, 0, 0, 
                        1, 0, 0, 0, 
                        1, 0, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0,
                        1, 1, 0, 0,
                        0, 0, 1, 0 },
                    { 0, 0, 0, 0, 
                        0, 1, 0, 0,
                        0, 1, 0, 0, 
                        1, 0, 0, 0 } 
                },
                //new-shape6
                { 
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        0, 1, 0, 0, 
                        1, 0, 1, 0 },
                    { 0, 0, 0, 0,
                        1, 0, 0, 0, 
                        0, 1, 0, 0, 
                        1, 0, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0,
                        1, 0, 1, 0,
                        0, 1, 0, 0 },
                    { 0, 0, 0, 0, 
                        0, 1, 0, 0,
                        1, 0, 0, 0, 
                        0, 1, 0, 0 } 
                },
                //new-shape7
                { 
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        0, 0, 0, 0, 
                        1, 1, 1, 0 },
                    { 0, 0, 0, 0,
                        0, 1, 0, 0, 
                        0, 1, 0, 0, 
                        0, 1, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0,
                        0, 0, 0, 0,
                        1, 1, 1, 0 },
                    { 0, 0, 0, 0, 
                        0, 1, 0, 0,
                        0, 1, 0, 0, 
                        0, 1, 0, 0 } 
                },
                //new-shape8
                { 
                    { 0, 0, 0, 0,
                        0, 0, 1, 0, 
                        0, 1, 0, 0, 
                        1, 0, 0, 0 },
                    { 0, 0, 0, 0,
                        1, 0, 0, 0, 
                        0, 1, 0, 0, 
                        0, 0, 1, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 1, 0,
                        0, 1, 0, 0,
                        1, 0, 0, 0 },
                    { 0, 0, 0, 0, 
                        1, 0, 0, 0,
                        0, 1, 0, 0, 
                        0, 0, 1, 0 } 
                },
                //new-shape9
                { 
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        0, 0, 1, 0, 
                        1, 1, 0, 0 },
                    { 0, 0, 0, 0,
                        1, 0, 0, 0, 
                        1, 0, 0, 0, 
                        0, 1, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0,
                        0, 1, 1, 0,
                        1, 0, 0, 0 },
                    { 0, 0, 0, 0, 
                        1, 0, 0, 0,
                        0, 1, 0, 0, 
                        0, 1, 0, 0 } 
                },
            };

        public void newblockparam()
        { 
            blockType = nextb;
            turnState = nextt;
            nextb = ran.nextInt(shape);
            nextt = ran.nextInt(4);
            x = 4;
            y = 0;

            if (gameover(x, y))
            {
                add(x,y,blockType,turnState);
                timer.stop();
                JOptionPane.showMessageDialog(null,"GAME OVER!", "DONE", JOptionPane.PLAIN_MESSAGE);
                timer.setDelay(400);
                shape = 7;
                timer.start();
                newmapparam();
                setboundary();
                score = 0;
                level = 1;
                showline = 0;
            }
        }

        //detect whether reach the left/right boundary or bottom or on top of another block
        public boolean stop(int x, int y, int blockType, int turnState)
        {
            for (int a = 0; a < 4; a++) 
            {
                for (int b = 0; b < 4; b++)
                {
                    if (((shapes[blockType][turnState][a * 4 + b] == 1) && (map[x + b + 1][y + a] == 1))
                    || ((shapes[blockType][turnState][a * 4 + b] == 1) && (map[x + b + 1][y + a] == 2)))
                    {
                        return true;
                    }
                }
            }
            return false;
        }

        //initialize game map
        public void newmapparam()
        {
            for (i = 0; i < 12; i++)
            {
                for (j = 0; j < 26; j++) map[i][j] = 0;
            }
        }

        //boundary drawing indicator
        public void setboundary()
        {
            for (i = 0; i < 12; i++)
            {
                map[i][25] = 2;
            }
            for (j = 0; j < 26; j++)
            {
                map[11][j] = 2;
                map[0][j] = 2;
            }
        }

        //clockwise turn
        public void turn()
        {
            int tempState = turnState;
            turnState = (turnState + 1) % 4;
            if (!stop(x, y, blockType, turnState)) {}
            if (stop(x, y, blockType, turnState)) turnState = tempState;
            repaint();
        }

        //counter-clockwise turn
        public void cturn()
        {
            int tempState = turnState;
            turnState = (turnState + 3) % 4;
            if (!stop(x, y, blockType, turnState)) {}
            if (stop(x, y, blockType, turnState)) turnState = tempState;
            repaint();
        }

        //left shift
        public void left()
        {
            if (!stop(x - 1, y, blockType, turnState)) 
            {
                x = x - 1;
            }
            repaint();
        }

        //right shift
        public void right()
        {
            if (!stop(x + 1, y, blockType, turnState))
            {
                x = x + 1;
            }
            repaint();
        }

        //determine whether game is over
        public boolean gameover(int x, int y){
            if (stop(x, y, blockType, turnState))
            {
                return true;
            }else
            {
                return false;
            }
        }

        //delete line method
        public void delline()
        {
            int c = 0;
            int i = 0;  //total lines deleted for current one shot
            for (int b = 0; b < 26; b++)
            {
                for (int a = 0; a < 12; a++)
                {
                    if (map[a][b] == 1)
                    {
                        c = c + 1;
                        if (c == 10) 
                        {
                            i = i + 1;
                            for (int d = b; d > 0; d--) 
                            {
                                for (int e = 0; e < 11; e++) 
                                {
                                    map[e][d] = map[e][d - 1];
                                }
                            }
                        }
                    }
                }
                c = 0;
            }
            //determine score earned for this shot
            switch (i) 
            {
                case 1:
                score = score + level*scoreFactor;
                line++;
                showline++;
                break;
                case 2:
                score = score + 2*level*scoreFactor;
                line+=2;
                showline+=2;
                break;
                case 3:
                score = score + 3*level*scoreFactor;
                line+=3;
                showline+=3;
                break;
                case 4:
                score = score + 4*level*scoreFactor;
                line+=4;
                showline+=4;
                break;
                default:
                break;
            }
            if (line >= row) levelup(); //level up if current level lines exceed the set number of lines required for each level
        }

        //add current status to map
        public void add(int x, int y, int blockType, int turnState)
        {
            int j = 0;
            for (int a = 0; a < 4; a++)
            {
                for (int b = 0; b < 4; b++)
                {
                    if (map[x + b + 1][y + a] == 0)
                    {
                        map[x + b + 1][y + a] = shapes[blockType][turnState][j];
                    }
                    j++;
                }
            }
        }

        //paint method to draw blocks
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);

            Dimension d = getSize();
            //current block
            for (j = 0; j < 16; j++)
            {
                if (shapes[blockType][turnState][j] == 1)
                {
                    g.setColor(Color.BLUE);
                    g.fillRect((j%4 + x + 1)*d.width/20,(j/4 + y - 4)*d.width/20, d.width/20, d.width/20);
                    g.setColor(Color.BLACK);
                    g.drawRect((j%4 + x + 1)*d.width/20,(j/4 + y - 4)*d.width/20, d.width/20, d.width/20);
                }
            }
            //next block
            for (j = 0; j < 16; j++)
            {
                if (shapes[nextb][nextt][j] == 1)
                {
                    g.setColor(Color.RED);
                    g.fillRect((j%4 + 1)*d.width/20+d.width*13/20,(j/4)*d.width/20+d.width/10, d.width/20, d.width/20);
                    g.setColor(Color.BLACK);
                    g.drawRect((j%4 + 1)*d.width/20+d.width*13/20,(j/4)*d.width/20+d.width/10, d.width/20, d.width/20);

                }
            }
            //left boundary drawing
            for (j = 4; j < 26; j++)
            { 
                int i = 0;
                if (map[i][j]==2)
                { 
                    g.setColor(Color.BLACK);
                    g.drawLine((i+1)*d.width/20,(j-4)*d.width/20,(i+1)*d.width/20,(j-5)*d.width/20);
                }
            }
            //right boundary drawing
            for (j = 4; j < 26; j++)
            { 
                int i = 11;
                if (map[i][j]==2)
                { 
                    g.setColor(Color.BLACK);
                    g.drawLine(i*d.width/20,(j-4)*d.width/20,i*d.width/20,(j-5)*d.width/20);
                }
            }
            //bottom boundary drawing
            for (i = 0; i < 10; i++)
            {
                int j = 25;
                g.setColor(Color.BLACK);
                g.drawLine((i+1)*d.width/20,(j-4)*d.width/20,(i+2)*d.width/20,(j-4)*d.width/20);
            }
            //draw blocks which reached the bottom or on top of another block
            for (j = 4; j < 26; j++)
            {
                for (i = 0; i < 12; i++)
                {
                    if (map[i][j] == 1)
                    { 
                        g.setColor(Color.GREEN);
                        g.fillRect(i*d.width/20,(j-4)*d.width/20, d.width/20, d.width/20);
                        g.setColor(Color.BLACK);
                        g.drawRect(i*d.width/20,(j-4)*d.width/20, d.width/20, d.width/20);
                    }              
                }
            }

            //next label
            g.setColor(Color.BLACK);
            g.setFont(new Font("Serif", Font.ITALIC, d.width/20));
            g.drawString("Next Shape：", d.width*13/20, d.width*3/40);
            //level label
            g.setColor(Color.black);
            g.setFont(new Font("Serif", Font.BOLD, d.width*3/80));
            g.drawString("Level:   " + level, d.width*13/20, d.width/2);
            //line label
            g.setColor(Color.black);
            g.setFont(new Font("Serif", Font.BOLD, d.width*3/80));
            g.drawString("Line:     " + showline, d.width*13/20, d.width*3/5);
            //score label
            g.setColor(Color.BLACK);
            g.setFont(new Font("Serif", Font.BOLD, d.width*3/80));
            g.drawString("Score:   " + score, d.width*13/20, d.width*7/10);
        }

        //level up method
        public void levelup()
        {
            timer.setDelay((int) (timer.getDelay()/(1 + level*speedFactor)));
            level++;
            line = 0;
        }

        //timerlistener for block falling down
        class TimerListener implements ActionListener 
        {
            public void actionPerformed(ActionEvent e)
            {
                repaint();
                if (!stop(x, y + 1, blockType, turnState))
                {
                    y = y + 1;
                    delline();
                }
                if (stop(x, y + 1, blockType, turnState))
                {
                    if (statusFlag == true) 
                    {
                        add(x, y, blockType, turnState);
                        delline();
                        newblockparam();
                        statusFlag = false;
                    }
                    statusFlag = true;
                }
            }
        }
    }

    // create a 20X20 Tetrisblock class
    class Tetrisblock1 extends JPanel
    {
        Random ran = new Random();
        public int blockType; //block type
        public int turnState; //block status
        public int x; //initial x-coordinate of block
        public int y; //initial y-coordinate of block
        public int nextb = ran.nextInt(shape); //next block type
        public int nextt = ran.nextInt(4); //next block status
        private int i = 0;
        private int j = 0;
        private boolean statusFlag = false;
        int[][] map = new int[23][27];

        public int score = 0;
        public int line = 0;
        public int level = 1;
        public int showline = 0;

        Tetrisblock1()
        {
            newblockparam();
            newmapparam();
            setboundary();
        }

        //3-D array to display different shapes. 1st d: block type, 2nd d: turn state, 3rd d: drawing param
        private final int shapes[][][] = new int[][][] 
            {
                // line-shape
                { 
                    { 0, 0, 0, 0, 
                        0, 0, 0, 0, 
                        0, 0, 0, 0, 
                        1, 1, 1, 1 },
                    { 0, 1, 0, 0,
                        0, 1, 0, 0, 
                        0, 1, 0, 0, 
                        0, 1, 0, 0 },
                    { 0, 0, 0, 0, 
                        0, 0, 0, 0, 
                        0, 0, 0, 0, 
                        1, 1, 1, 1 },
                    { 0, 1, 0, 0, 
                        0, 1, 0, 0, 
                        0, 1, 0, 0, 
                        0, 1, 0, 0 }
                },
                // s-shape
                { 
                    { 0, 0, 0, 0, 
                        0, 0, 0, 0, 
                        0, 1, 1, 0, 
                        1, 1, 0, 0 },
                    { 0, 0, 0, 0, 
                        1, 0, 0, 0, 
                        1, 1, 0, 0, 
                        0, 1, 0, 0 },
                    { 0, 0, 0, 0, 
                        0, 0, 0, 0,
                        0, 1, 1, 0, 
                        1, 1, 0, 0 },
                    { 0, 0, 0, 0,
                        1, 0, 0, 0, 
                        1, 1, 0, 0,
                        0, 1, 0, 0 } 
                },
                // z-shape
                { 
                    { 0, 0, 0, 0, 
                        0, 0, 0, 0, 
                        1, 1, 0, 0, 
                        0, 1, 1, 0 },
                    { 0, 0, 0, 0, 
                        0, 1, 0, 0, 
                        1, 1, 0, 0, 
                        1, 0, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        1, 1, 0, 0, 
                        0, 1, 1, 0 },
                    { 0, 0, 0, 0,
                        0, 1, 0, 0, 
                        1, 1, 0, 0, 
                        1, 0, 0, 0 } 
                },
                // mirror-l-shape
                { 
                    { 0, 0, 0, 0, 
                        0, 1, 0, 0, 
                        0, 1, 0, 0, 
                        1, 1, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        1, 0, 0, 0, 
                        1, 1, 1, 0 },
                    { 0, 0, 0, 0,
                        1, 1, 0, 0,
                        1, 0, 0, 0, 
                        1, 0, 0, 0 },
                    { 0, 0, 0, 0, 
                        0, 0, 0, 0, 
                        1, 1, 1, 0, 
                        0, 0, 1, 0 } 
                },
                // square-shape
                { 
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        1, 1, 0, 0, 
                        1, 1, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        1, 1, 0, 0, 
                        1, 1, 0, 0 },
                    { 0, 0, 0, 0, 
                        0, 0, 0, 0, 
                        1, 1, 0, 0, 
                        1, 1, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        1, 1, 0, 0, 
                        1, 1, 0, 0 } 
                },
                // l-shape
                { 
                    { 0, 0, 0, 0,
                        1, 0, 0, 0, 
                        1, 0, 0, 0,
                        1, 1, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        1, 1, 1, 0,
                        1, 0, 0, 0 },
                    { 0, 0, 0, 0,
                        1, 1, 0, 0, 
                        0, 1, 0, 0, 
                        0, 1, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        0, 0, 1, 0, 
                        1, 1, 1, 0 } 
                },
                // t-shape
                { 
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        0, 1, 0, 0, 
                        1, 1, 1, 0 },
                    { 0, 0, 0, 0,
                        0, 1, 0, 0, 
                        1, 1, 0, 0, 
                        0, 1, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0,
                        1, 1, 1, 0,
                        0, 1, 0, 0 },
                    { 0, 0, 0, 0, 
                        0, 1, 0, 0,
                        0, 1, 1, 0, 
                        0, 1, 0, 0 } 
                },
                //new-shape1
                { 
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        0, 0, 0, 0, 
                        1, 0, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        0, 0, 0, 0, 
                        1, 0, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0,
                        0, 0, 0, 0,
                        1, 0, 0, 0 },
                    { 0, 0, 0, 0, 
                        0, 0, 0, 0,
                        0, 0, 0, 0, 
                        1, 0, 0, 0 } 
                },

                //new-shape2
                { 
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        0, 1, 0, 0, 
                        1, 0, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        1, 0, 0, 0, 
                        0, 1, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0,
                        0, 1, 0, 0,
                        1, 0, 0, 0 },
                    { 0, 0, 0, 0, 
                        0, 0, 0, 0,
                        1, 0, 0, 0, 
                        0, 1, 0, 0 } 
                },
                //new-shape3
                { 
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        0, 1, 0, 0, 
                        0, 1, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        0, 0, 0, 0, 
                        1, 1, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0,
                        0, 1, 0, 0,
                        0, 1, 0, 0 },
                    { 0, 0, 0, 0, 
                        0, 0, 0, 0,
                        0, 0, 0, 0, 
                        1, 1, 0, 0 } 
                },
                //new-shape4
                { 
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        0, 1, 0, 0, 
                        1, 1, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        1, 0, 0, 0, 
                        1, 1, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0,
                        1, 1, 0, 0,
                        1, 0, 0, 0 },
                    { 0, 0, 0, 0, 
                        0, 0, 0, 0,
                        1, 1, 0, 0, 
                        0, 1, 0, 0 } 
                },
                //new-shape5
                { 
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        1, 0, 0, 0, 
                        0, 1, 1, 0 },
                    { 0, 0, 0, 0,
                        0, 1, 0, 0, 
                        1, 0, 0, 0, 
                        1, 0, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0,
                        1, 1, 0, 0,
                        0, 0, 1, 0 },
                    { 0, 0, 0, 0, 
                        0, 1, 0, 0,
                        0, 1, 0, 0, 
                        1, 0, 0, 0 } 
                },
                //new-shape6
                { 
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        0, 1, 0, 0, 
                        1, 0, 1, 0 },
                    { 0, 0, 0, 0,
                        1, 0, 0, 0, 
                        0, 1, 0, 0, 
                        1, 0, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0,
                        1, 0, 1, 0,
                        0, 1, 0, 0 },
                    { 0, 0, 0, 0, 
                        0, 1, 0, 0,
                        1, 0, 0, 0, 
                        0, 1, 0, 0 } 
                },
                //new-shape7
                { 
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        0, 0, 0, 0, 
                        1, 1, 1, 0 },
                    { 0, 0, 0, 0,
                        0, 1, 0, 0, 
                        0, 1, 0, 0, 
                        0, 1, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0,
                        0, 0, 0, 0,
                        1, 1, 1, 0 },
                    { 0, 0, 0, 0, 
                        0, 1, 0, 0,
                        0, 1, 0, 0, 
                        0, 1, 0, 0 } 
                },
                //new-shape8
                { 
                    { 0, 0, 0, 0,
                        0, 0, 1, 0, 
                        0, 1, 0, 0, 
                        1, 0, 0, 0 },
                    { 0, 0, 0, 0,
                        1, 0, 0, 0, 
                        0, 1, 0, 0, 
                        0, 0, 1, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 1, 0,
                        0, 1, 0, 0,
                        1, 0, 0, 0 },
                    { 0, 0, 0, 0, 
                        1, 0, 0, 0,
                        0, 1, 0, 0, 
                        0, 0, 1, 0 } 
                },
                //new-shape9
                { 
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        0, 0, 1, 0, 
                        1, 1, 0, 0 },
                    { 0, 0, 0, 0,
                        1, 0, 0, 0, 
                        1, 0, 0, 0, 
                        0, 1, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0,
                        0, 1, 1, 0,
                        1, 0, 0, 0 },
                    { 0, 0, 0, 0, 
                        1, 0, 0, 0,
                        0, 1, 0, 0, 
                        0, 1, 0, 0 } 
                },
            };

        public void newblockparam()
        { 
            blockType = nextb;
            turnState = nextt;
            nextb = ran.nextInt(shape);
            nextt = ran.nextInt(4);
            x = 9;
            y = 0;

            if (gameover(x, y))
            {
                add(x,y,blockType,turnState);
                timer1.stop();
                JOptionPane.showMessageDialog(null,"GAME OVER!", "DONE", JOptionPane.PLAIN_MESSAGE);
                timer1.setDelay(400);
                shape = 7;
                timer1.start();
                newmapparam();
                setboundary();
                score = 0;
                level = 1;
                showline = 0;
            }
        }

        //detect whether reach the left/right boundary or bottom or on top of another block
        public boolean stop(int x, int y, int blockType, int turnState)
        {
            for (int a = 0; a < 4; a++) 
            {
                for (int b = 0; b < 4; b++)
                {
                    if (((shapes[blockType][turnState][a * 4 + b] == 1) && (map[x + b + 1][y + a] == 1))
                    || ((shapes[blockType][turnState][a * 4 + b] == 1) && (map[x + b + 1][y + a] == 2)))
                    {
                        return true;
                    }
                }
            }
            return false;
        }

        //initialize game map
        public void newmapparam()
        {
            for (i = 0; i < 21; i++)
            {
                for (j = 0; j < 26; j++) map[i][j] = 0;
            }
        }

        //boundary drawing indicator
        public void setboundary()
        {
            for (i = 0; i < 21; i++)
            {
                map[i][25] = 2;
            }
            for (j = 0; j < 26; j++)
            {
                map[21][j] = 2;
                map[0][j] = 2;
            }
        }

        //clockwise turn
        public void turn()
        {
            int tempState = turnState;
            turnState = (turnState + 1) % 4;
            if (!stop(x, y, blockType, turnState)) {}
            if (stop(x, y, blockType, turnState)) turnState = tempState;
            repaint();
        }

        //counter-clockwise turn
        public void cturn()
        {
            int tempState = turnState;
            turnState = (turnState + 3) % 4;
            if (!stop(x, y, blockType, turnState)) {}
            if (stop(x, y, blockType, turnState)) turnState = tempState;
            repaint();
        }

        //left shift
        public void left()
        {
            if (!stop(x - 1, y, blockType, turnState)) 
            {
                x = x - 1;
            }
            repaint();
        }

        //right shift
        public void right()
        {
            if (!stop(x + 1, y, blockType, turnState))
            {
                x = x + 1;
            }
            repaint();
        }

        //determine whether game over
        public boolean gameover(int x, int y)
        {
            if (stop(x, y, blockType, turnState))
            {
                return true;
            }else
            {
                return false;
            }
        }

        //delete line method
        public void delline()
        {
            int c = 0;
            int i = 0;  
            for (int b = 0; b < 26; b++)
            {
                for (int a = 0; a < 21; a++)
                {
                    if (map[a][b] == 1)
                    {
                        c = c + 1;
                        if (c == 20) 
                        {
                            i = i + 1;
                            for (int d = b; d > 0; d--) 
                            {
                                for (int e = 0; e < 11; e++) 
                                {
                                    map[e][d] = map[e][d - 1];
                                }
                            }
                        }
                    }
                }
                c = 0;
            }
            //determine score earned
            switch (i) 
            {
                case 1:
                score = score + level*scoreFactor;
                line++;
                showline++;
                break;
                case 2:
                score = score + 2*level*scoreFactor;
                line+=2;
                showline+=2;
                break;
                case 3:
                score = score + 3*level*scoreFactor;
                line+=3;
                showline+=3;
                break;
                case 4:
                score = score + 4*level*scoreFactor;
                line+=4;
                showline+=4;
                break;
                default:
                break;
            }
            if (line >= row) levelup();
        }

        //add current status to map
        public void add(int x, int y, int blockType, int turnState)
        {
            int j = 0;
            for (int a = 0; a < 4; a++)
            {
                for (int b = 0; b < 4; b++)
                {
                    if (map[x + b + 1][y + a] == 0)
                    {
                        map[x + b + 1][y + a] = shapes[blockType][turnState][j];
                    }
                    j++;
                }
            }
        }

        //paint method to draw blocks
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);

            Dimension d = getSize();
            //current block
            for (j = 0; j < 16; j++)
            {
                if (shapes[blockType][turnState][j] == 1)
                {
                    g.setColor(Color.BLUE);
                    g.fillRect((j%4 + x + 1)*d.width/30,(j/4 + y - 4)*d.width/30, d.width/30, d.width/30);
                    g.setColor(Color.BLACK);
                    g.drawRect((j%4 + x + 1)*d.width/30,(j/4 + y - 4)*d.width/30, d.width/30, d.width/30);
                }
            }
            //next block
            for (j = 0; j < 16; j++)
            {
                if (shapes[nextb][nextt][j] == 1)
                {
                    g.setColor(Color.RED);
                    g.fillRect((j%4 + 1)*d.width/30+d.width*23/30,(j/4)*d.width/30+d.width/15, d.width/30, d.width/30);
                    g.setColor(Color.BLACK);
                    g.drawRect((j%4 + 1)*d.width/30+d.width*23/30,(j/4)*d.width/30+d.width/15, d.width/30, d.width/30);

                }
            }
            //left boundary drawing
            for (j = 4; j < 26; j++)
            { 
                int i = 0;
                if (map[i][j]==2)
                { 
                    g.setColor(Color.BLACK);
                    g.drawLine((i+1)*d.width/30,(j-4)*d.width/30,(i+1)*d.width/30,(j-5)*d.width/30);
                }
            }
            //right boundary drawing
            for (j = 4; j < 26; j++)
            { 
                int i = 21;
                if (map[i][j]==2)
                { 
                    g.setColor(Color.BLACK);
                    g.drawLine(i*d.width/30,(j-4)*d.width/30,i*d.width/30,(j-5)*d.width/30);
                }
            }
            //bottom boundary drawing
            for (i = 0; i < 20; i++)
            {
                int j = 25;
                g.setColor(Color.BLACK);
                g.drawLine((i+1)*d.width/30,(j-4)*d.width/30,(i+2)*d.width/30,(j-4)*d.width/30);
            }
            //draw blocks which reached the bottom or on top of another block
            for (j = 4; j < 26; j++)
            {
                for (i = 0; i < 21; i++)
                {
                    if (map[i][j] == 1)
                    { 
                        g.setColor(Color.GREEN);
                        g.fillRect(i*d.width/30,(j-4)*d.width/30, d.width/30, d.width/30);
                        g.setColor(Color.BLACK);
                        g.drawRect(i*d.width/30,(j-4)*d.width/30, d.width/30, d.width/30);
                    }              
                }
            }

            //next label
            g.setColor(Color.BLACK);
            g.setFont(new Font("Serif", Font.ITALIC, d.width/30));
            g.drawString("Next Shape：", d.width*23/30, d.width*3/60);
            //level label
            g.setColor(Color.black);
            g.setFont(new Font("Serif", Font.BOLD, d.width*3/120));
            g.drawString("Level:    " + level, d.width*23/30, d.width/3);
            //line label
            g.setColor(Color.black);
            g.setFont(new Font("Serif", Font.BOLD, d.width*3/120));
            g.drawString("Line:     " + showline, d.width*23/30, d.width*2/5);
            //score label
            g.setColor(Color.BLACK);
            g.setFont(new Font("Serif", Font.BOLD, d.width*3/120));
            g.drawString("Score:   " + score, d.width*23/30, d.width*7/15);
        }

        //level up method
        public void levelup()
        {
            timer1.setDelay((int) (timer1.getDelay()/(1 + level*speedFactor)));
            level++;
            line = 0;
        }

        //timerlistener for block falling down
        class TimerListener implements ActionListener 
        {
            public void actionPerformed(ActionEvent e)
            {
                repaint();
                if (!stop(x, y + 1, blockType, turnState))
                {
                    y = y + 1;
                    delline();
                }
                if (stop(x, y + 1, blockType, turnState))
                {
                    if (statusFlag == true) 
                    {
                        add(x, y, blockType, turnState);
                        delline();
                        newblockparam();
                        statusFlag = false;
                    }
                    statusFlag = true;
                }
            }
        }
    }

    // create a 10X30 Tetrisblock class
    class Tetrisblock2 extends JPanel
    {
        Random ran = new Random();
        public int blockType; //block type
        public int turnState; //block status
        public int x; //initial x-coordinate of block
        public int y; //initial y-coordinate of block
        public int nextb = ran.nextInt(shape); //next block type
        public int nextt = ran.nextInt(4); //next block status
        private int i = 0;
        private int j = 0;
        private boolean statusFlag = false;
        int[][] map = new int[13][37];

        public int score = 0; //init. score
        public int line = 0;  //init. line deleted at current level
        public int level = 1; //init. level
        public int showline = 0; //init. total line deleted

        Tetrisblock2()
        {
            newblockparam();
            newmapparam();
            setboundary();
        }

        //3-D array to display different shapes. 1st d: block type, 2nd d: turn state, 3rd d: drawing param
        private final int shapes[][][] = new int[][][] 
            {
                // line-shape
                { 
                    { 0, 0, 0, 0, 
                        0, 0, 0, 0, 
                        0, 0, 0, 0, 
                        1, 1, 1, 1 },
                    { 0, 1, 0, 0,
                        0, 1, 0, 0, 
                        0, 1, 0, 0, 
                        0, 1, 0, 0 },
                    { 0, 0, 0, 0, 
                        0, 0, 0, 0, 
                        0, 0, 0, 0, 
                        1, 1, 1, 1 },
                    { 0, 1, 0, 0, 
                        0, 1, 0, 0, 
                        0, 1, 0, 0, 
                        0, 1, 0, 0 }
                },
                // s-shape
                { 
                    { 0, 0, 0, 0, 
                        0, 0, 0, 0, 
                        0, 1, 1, 0, 
                        1, 1, 0, 0 },
                    { 0, 0, 0, 0, 
                        1, 0, 0, 0, 
                        1, 1, 0, 0, 
                        0, 1, 0, 0 },
                    { 0, 0, 0, 0, 
                        0, 0, 0, 0,
                        0, 1, 1, 0, 
                        1, 1, 0, 0 },
                    { 0, 0, 0, 0,
                        1, 0, 0, 0, 
                        1, 1, 0, 0,
                        0, 1, 0, 0 } 
                },
                // z-shape
                { 
                    { 0, 0, 0, 0, 
                        0, 0, 0, 0, 
                        1, 1, 0, 0, 
                        0, 1, 1, 0 },
                    { 0, 0, 0, 0, 
                        0, 1, 0, 0, 
                        1, 1, 0, 0, 
                        1, 0, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        1, 1, 0, 0, 
                        0, 1, 1, 0 },
                    { 0, 0, 0, 0,
                        0, 1, 0, 0, 
                        1, 1, 0, 0, 
                        1, 0, 0, 0 } 
                },
                // mirror-l-shape
                { 
                    { 0, 0, 0, 0, 
                        0, 1, 0, 0, 
                        0, 1, 0, 0, 
                        1, 1, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        1, 0, 0, 0, 
                        1, 1, 1, 0 },
                    { 0, 0, 0, 0,
                        1, 1, 0, 0,
                        1, 0, 0, 0, 
                        1, 0, 0, 0 },
                    { 0, 0, 0, 0, 
                        0, 0, 0, 0, 
                        1, 1, 1, 0, 
                        0, 0, 1, 0 } 
                },
                // square-shape
                { 
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        1, 1, 0, 0, 
                        1, 1, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        1, 1, 0, 0, 
                        1, 1, 0, 0 },
                    { 0, 0, 0, 0, 
                        0, 0, 0, 0, 
                        1, 1, 0, 0, 
                        1, 1, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        1, 1, 0, 0, 
                        1, 1, 0, 0 } 
                },
                // l-shape
                { 
                    { 0, 0, 0, 0,
                        1, 0, 0, 0, 
                        1, 0, 0, 0,
                        1, 1, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        1, 1, 1, 0,
                        1, 0, 0, 0 },
                    { 0, 0, 0, 0,
                        1, 1, 0, 0, 
                        0, 1, 0, 0, 
                        0, 1, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        0, 0, 1, 0, 
                        1, 1, 1, 0 } 
                },
                // t-shape
                { 
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        0, 1, 0, 0, 
                        1, 1, 1, 0 },
                    { 0, 0, 0, 0,
                        0, 1, 0, 0, 
                        1, 1, 0, 0, 
                        0, 1, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0,
                        1, 1, 1, 0,
                        0, 1, 0, 0 },
                    { 0, 0, 0, 0, 
                        0, 1, 0, 0,
                        0, 1, 1, 0, 
                        0, 1, 0, 0 } 
                },
                //new-shape1
                { 
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        0, 0, 0, 0, 
                        1, 0, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        0, 0, 0, 0, 
                        1, 0, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0,
                        0, 0, 0, 0,
                        1, 0, 0, 0 },
                    { 0, 0, 0, 0, 
                        0, 0, 0, 0,
                        0, 0, 0, 0, 
                        1, 0, 0, 0 } 
                },

                //new-shape2
                { 
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        0, 1, 0, 0, 
                        1, 0, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        1, 0, 0, 0, 
                        0, 1, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0,
                        0, 1, 0, 0,
                        1, 0, 0, 0 },
                    { 0, 0, 0, 0, 
                        0, 0, 0, 0,
                        1, 0, 0, 0, 
                        0, 1, 0, 0 } 
                },

                //new-shape3
                { 
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        0, 1, 0, 0, 
                        0, 1, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        0, 0, 0, 0, 
                        1, 1, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0,
                        0, 1, 0, 0,
                        0, 1, 0, 0 },
                    { 0, 0, 0, 0, 
                        0, 0, 0, 0,
                        0, 0, 0, 0, 
                        1, 1, 0, 0 } 
                },

                //new-shape4
                { 
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        0, 1, 0, 0, 
                        1, 1, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        1, 0, 0, 0, 
                        1, 1, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0,
                        1, 1, 0, 0,
                        1, 0, 0, 0 },
                    { 0, 0, 0, 0, 
                        0, 0, 0, 0,
                        1, 1, 0, 0, 
                        0, 1, 0, 0 } 
                },
                //new-shape5
                { 
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        1, 0, 0, 0, 
                        0, 1, 1, 0 },
                    { 0, 0, 0, 0,
                        0, 1, 0, 0, 
                        1, 0, 0, 0, 
                        1, 0, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0,
                        1, 1, 0, 0,
                        0, 0, 1, 0 },
                    { 0, 0, 0, 0, 
                        0, 1, 0, 0,
                        0, 1, 0, 0, 
                        1, 0, 0, 0 } 
                },
                //new-shape6
                { 
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        0, 1, 0, 0, 
                        1, 0, 1, 0 },
                    { 0, 0, 0, 0,
                        1, 0, 0, 0, 
                        0, 1, 0, 0, 
                        1, 0, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0,
                        1, 0, 1, 0,
                        0, 1, 0, 0 },
                    { 0, 0, 0, 0, 
                        0, 1, 0, 0,
                        1, 0, 0, 0, 
                        0, 1, 0, 0 } 
                },
                //new-shape7
                { 
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        0, 0, 0, 0, 
                        1, 1, 1, 0 },
                    { 0, 0, 0, 0,
                        0, 1, 0, 0, 
                        0, 1, 0, 0, 
                        0, 1, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0,
                        0, 0, 0, 0,
                        1, 1, 1, 0 },
                    { 0, 0, 0, 0, 
                        0, 1, 0, 0,
                        0, 1, 0, 0, 
                        0, 1, 0, 0 } 
                },
                //new-shape8
                { 
                    { 0, 0, 0, 0,
                        0, 0, 1, 0, 
                        0, 1, 0, 0, 
                        1, 0, 0, 0 },
                    { 0, 0, 0, 0,
                        1, 0, 0, 0, 
                        0, 1, 0, 0, 
                        0, 0, 1, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 1, 0,
                        0, 1, 0, 0,
                        1, 0, 0, 0 },
                    { 0, 0, 0, 0, 
                        1, 0, 0, 0,
                        0, 1, 0, 0, 
                        0, 0, 1, 0 } 
                },
                //new-shape9
                { 
                    { 0, 0, 0, 0,
                        0, 0, 0, 0, 
                        0, 0, 1, 0, 
                        1, 1, 0, 0 },
                    { 0, 0, 0, 0,
                        1, 0, 0, 0, 
                        1, 0, 0, 0, 
                        0, 1, 0, 0 },
                    { 0, 0, 0, 0,
                        0, 0, 0, 0,
                        0, 1, 1, 0,
                        1, 0, 0, 0 },
                    { 0, 0, 0, 0, 
                        1, 0, 0, 0,
                        0, 1, 0, 0, 
                        0, 1, 0, 0 } 
                },
            };

        public void newblockparam()
        { 
            blockType = nextb;
            turnState = nextt;
            nextb = ran.nextInt(shape);
            nextt = ran.nextInt(4);
            x = 4;
            y = 0;

            if (gameover(x, y))
            {
                add(x,y,blockType,turnState);
                timer2.stop();
                JOptionPane.showMessageDialog(null,"GAME OVER!", "DONE", JOptionPane.PLAIN_MESSAGE);
                timer2.setDelay(400);
                shape = 7;
                timer2.start();
                newmapparam();
                setboundary();
                score = 0;
                level = 1;
                showline = 0;
            }
        }

        //detect whether reach the left/right boundary or bottom or on top of another block
        public boolean stop(int x, int y, int blockType, int turnState)
        {
            for (int a = 0; a < 4; a++) 
            {
                for (int b = 0; b < 4; b++)
                {
                    if (((shapes[blockType][turnState][a * 4 + b] == 1) && (map[x + b + 1][y + a] == 1))
                    || ((shapes[blockType][turnState][a * 4 + b] == 1) && (map[x + b + 1][y + a] == 2)))
                    {
                        return true;
                    }
                }
            }
            return false;
        }

        //initialize game map
        public void newmapparam()
        {
            for (i = 0; i < 12; i++)
            {
                for (j = 0; j < 36; j++) map[i][j] = 0;
            }
        }

        //boundary drawing indicator
        public void setboundary()
        {
            for (i = 0; i < 12; i++)
            {
                map[i][35] = 2;
            }
            for (j = 0; j < 36; j++)
            {
                map[11][j] = 2;
                map[0][j] = 2;
            }
        }

        //clockwise turn
        public void turn()
        {
            int tempState = turnState;
            turnState = (turnState + 1) % 4;
            if (!stop(x, y, blockType, turnState)) {}
            if (stop(x, y, blockType, turnState)) turnState = tempState;
            repaint();
        }

        //counter-clockwise turn
        public void cturn()
        {
            int tempState = turnState;
            turnState = (turnState + 3) % 4;
            if (!stop(x, y, blockType, turnState)) {}
            if (stop(x, y, blockType, turnState)) turnState = tempState;
            repaint();
        }

        //left shift
        public void left()
        {
            if (!stop(x - 1, y, blockType, turnState)) 
            {
                x = x - 1;
            }
            repaint();
        }

        //right shift
        public void right()
        {
            if (!stop(x + 1, y, blockType, turnState))
            {
                x = x + 1;
            }
            repaint();
        }

        //determine whether game is over
        public boolean gameover(int x, int y)
        {
            if (stop(x, y, blockType, turnState)){
                return true;
            }else{
                return false;
            }
        }

        //delete line method
        public void delline()
        {
            int c = 0;
            int i = 0;  //total lines deleted for current one shot
            for (int b = 0; b < 36; b++)
            {
                for (int a = 0; a < 12; a++)
                {
                    if (map[a][b] == 1)
                    {
                        c = c + 1;
                        if (c == 10) 
                        {
                            i = i + 1;
                            for (int d = b; d > 0; d--) 
                            {
                                for (int e = 0; e < 11; e++) 
                                {
                                    map[e][d] = map[e][d - 1];
                                }
                            }
                        }
                    }
                }
                c = 0;
            }
            //determine score earned for this shot
            switch (i) 
            {
                case 1:
                score = score + level*scoreFactor;
                line++;
                showline++;
                break;
                case 2:
                score = score + 2*level*scoreFactor;
                line+=2;
                showline+=2;
                break;
                case 3:
                score = score + 3*level*scoreFactor;
                line+=3;
                showline+=3;
                break;
                case 4:
                score = score + 4*level*scoreFactor;
                line+=4;
                showline+=4;
                break;
                default:
                break;
            }
            if (line >= row) levelup(); //level up if current level lines exceed the set number of lines required for each level
        }

        //add current status to map
        public void add(int x, int y, int blockType, int turnState)
        {
            int j = 0;
            for (int a = 0; a < 4; a++)
            {
                for (int b = 0; b < 4; b++)
                {
                    if (map[x + b + 1][y + a] == 0)
                    {
                        map[x + b + 1][y + a] = shapes[blockType][turnState][j];
                    }
                    j++;
                }
            }
        }

        //paint method to draw blocks
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);

            Dimension d = getSize();
            //current block
            for (j = 0; j < 16; j++)
            {
                if (shapes[blockType][turnState][j] == 1)
                {
                    g.setColor(Color.BLUE);
                    g.fillRect((j%4 + x + 1)*d.width/20,(j/4 + y - 4)*d.width/20, d.width/20, d.width/20);
                    g.setColor(Color.BLACK);
                    g.drawRect((j%4 + x + 1)*d.width/20,(j/4 + y - 4)*d.width/20, d.width/20, d.width/20);
                }
            }
            //next block
            for (j = 0; j < 16; j++)
            {
                if (shapes[nextb][nextt][j] == 1)
                {
                    g.setColor(Color.RED);
                    g.fillRect((j%4 + 1)*d.width/20+d.width*13/20,(j/4)*d.width/20+d.width*3/20, d.width/20, d.width/20);
                    g.setColor(Color.BLACK);
                    g.drawRect((j%4 + 1)*d.width/20+d.width*13/20,(j/4)*d.width/20+d.width*3/20, d.width/20, d.width/20);

                }
            }
            //left boundary drawing
            for (j = 4; j < 36; j++)
            { 
                int i = 0;
                if (map[i][j]==2)
                { 
                    g.setColor(Color.BLACK);
                    g.drawLine((i+1)*d.width/20,(j-4)*d.width/20,(i+1)*d.width/20,(j-5)*d.width/20);
                }
            }
            //right boundary drawing
            for (j = 4; j < 36; j++)
            { 
                int i = 11;
                if (map[i][j]==2)
                { 
                    g.setColor(Color.BLACK);
                    g.drawLine(i*d.width/20,(j-4)*d.width/20,i*d.width/20,(j-5)*d.width/20);
                }
            }
            //bottom boundary drawing
            for (i = 0; i < 10; i++)
            {
                int j = 35;
                g.setColor(Color.BLACK);
                g.drawLine((i+1)*d.width/20,(j-4)*d.width/20,(i+2)*d.width/20,(j-4)*d.width/20);
            }
            //draw blocks which reached the bottom or on top of another block
            for (j = 4; j < 36; j++)
            {
                for (i = 0; i < 12; i++)
                {
                    if (map[i][j] == 1)
                    { 
                        g.setColor(Color.GREEN);
                        g.fillRect(i*d.width/20,(j-4)*d.width/20, d.width/20, d.width/20);
                        g.setColor(Color.BLACK);
                        g.drawRect(i*d.width/20,(j-4)*d.width/20, d.width/20, d.width/20);
                    }              
                }
            }

            //next label
            g.setColor(Color.BLACK);
            g.setFont(new Font("Serif", Font.ITALIC, d.width/20));
            g.drawString("Next Shape：", d.width*13/20, d.width/8);
            //level label
            g.setColor(Color.black);
            g.setFont(new Font("Serif", Font.BOLD, d.width*3/80));
            g.drawString("Level:   " + level, d.width*13/20, d.width*5/8);
            //line label
            g.setColor(Color.black);
            g.setFont(new Font("Serif", Font.BOLD, d.width*3/80));
            g.drawString("Line:     " + showline, d.width*13/20, d.width*7/8);
            //score label
            g.setColor(Color.BLACK);
            g.setFont(new Font("Serif", Font.BOLD, d.width*3/80));
            g.drawString("Score:   " + score, d.width*13/20, d.width*9/8);
        }

        //level up method
        public void levelup()
        {
            timer2.setDelay((int) (timer2.getDelay()/(1 + level*speedFactor)));
            level++;
            line = 0;
        }

        //timerlistener for block falling down
        class TimerListener implements ActionListener 
        {
            public void actionPerformed(ActionEvent e)
            {
                repaint();
                if (!stop(x, y + 1, blockType, turnState))
                {
                    y = y + 1;
                    delline();
                }
                if (stop(x, y + 1, blockType, turnState))
                {
                    if (statusFlag == true) 
                    {
                        add(x, y, blockType, turnState);
                        delline();
                        newblockparam();
                        statusFlag = false;
                    }
                    statusFlag = true;
                }
            }
        }
    }
}
