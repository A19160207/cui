package snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Yard extends Frame {
	
  
	PaintThread paintThread = new  PaintThread();
	private boolean gameOver = false;
	
	public static final int ROWS=30;
	public static final int COLS=30;
    public static final int BLOCK_SIZE=15;
    
    private Font fontGameOver = new Font("宋体",Font.BOLD,50);
    
    private int score = 0;
    
    snake s = new snake(this);
    Egg e = new Egg();
    
    Image offScreenImage = null;
    
    public void launch(){
    	this.setLocation(200,200);
    	this.setSize(COLS*BLOCK_SIZE,ROWS*BLOCK_SIZE);
    	this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
    		
    		
    	});
    	this.setVisible(true);
    	this. addKeyListener(new KeyMonitor());
    	new Thread(paintThread).start();
    }
    
    
    
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
        new Yard().launch();
	}
	
	public void stop(){
		gameOver = true;
	}
	   @Override
		public void paint(Graphics g) {
			Color c=g.getColor();
			g.setColor(Color.GRAY);
			g.fillRect(0, 0, COLS*BLOCK_SIZE,ROWS*BLOCK_SIZE);
			g.setColor(Color.DARK_GRAY);
			//画出横线
			for(int i=1;i<ROWS;i++)
			{
				g.drawLine(0,BLOCK_SIZE*i,COLS*BLOCK_SIZE,BLOCK_SIZE*i);
			}
			//画出竖线
			for(int i=1;i<COLS;i++)
			{
				g.drawLine(BLOCK_SIZE*i,0,BLOCK_SIZE*i,BLOCK_SIZE*ROWS);
			}
			g.setColor(Color.YELLOW);
			g.drawString("score:" + score, 10, 60);
			if(gameOver){
				g.setFont(fontGameOver);
				g.drawString("游戏结束", 120, 180);
		        
				paintThread.gameOver();
			}
			g.setColor(c);
			
			s.eat(e);
			e.draw(g);
			s.draw(g);
			
			
		}
	   
	   
		@Override
		public void update(Graphics g) {
			if (offScreenImage == null){
				offScreenImage = this.createImage(COLS*BLOCK_SIZE,ROWS*BLOCK_SIZE);
			}
			Graphics g0ff = offScreenImage.getGraphics();
			paint(g0ff);
			g.drawImage(offScreenImage, 0, 0, null);
		}
		
		
	   private class PaintThread implements Runnable{

		private boolean running = true;
		public void run() {
		   while (running) {
			    repaint();
			    try {
				    Thread.sleep(100);
		       }catch(InterruptedException e){
		    	   e.printStackTrace();
		       }
			    
		   }
		   
	    }
		
		public void gameOver(){
			running =false;
		}
   }
	   
	   private class KeyMonitor extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
		    s.keyPressed(e);
		}
		   
	   }

	public int getScore() {
		return score;
	}



	public void setScore(int score) {
		this.score = score;
		
	}


	

}
