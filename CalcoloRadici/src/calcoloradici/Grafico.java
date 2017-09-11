/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package calcoloradici;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Sebastiano
 */
public class Grafico extends JPanel {

    boolean init;
    boolean disRadici=false;
    int funz = 0;
    double sx, dx;
    double passo;
    double approx;
    double unitax;
    double unitay;
    double asse;
    int gradoPol;
    ArrayList<Double> vettPol;
    double nradici;
    ArrayList<Double> vettRadici;

    public Grafico(int width, int height) {
        setSize(width, height);
        unitay = getHeight() / 2;
        init = true;
        disRadici = false;
        vettRadici = new ArrayList<Double>();
        vettPol = new ArrayList<Double>();
    }
    
    public void passaPol(ArrayList<Double> p){
        vettPol=p;
    }

    public void setDisRadici(boolean d) {
        disRadici = d;
    }

    public void pulisciRadici() {
        vettRadici.clear();
    }

    public void setGrado(int g) {
        gradoPol = g;
    }

    public void setFunz(int f) {
        /*
         * 0=sin(x)
         * 1=cos(x)
         * 2=polinomio
         */
        funz = f;
    }

    public void setSx(double s) {
        sx = s;
        unitax = (dx - sx) / getWidth();
    }

    public void setDx(double d) {
        dx = d;
        unitax = (dx - sx) / getWidth();
    }

    public void setPasso(double p) {
        passo = p;
    }

    public void setApprox(double a) {
        approx = a;
    }

    public void setInit(boolean i) {
        init = i;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLACK);
        g.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
        if (init) {
            g.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
        } else {
                if (sx < 0 && dx > 0) {
                    
                    asse =Math.abs( (sx / (dx - sx)));
                    asse*=getWidth();
                    g.drawLine((int) asse, 0, (int) asse, getHeight());
                }

                switch (funz) {
                    case 0:
                        sinX(g);
                        break;
                    case 1:
                        cosX(g);
                        break;
                    case 2:
                        polX(g);
                        break;
                }
                if (disRadici) {
                bisezione();
                disegnaRadici(g);
                disRadici=false;
                vettRadici.clear();
            }
            }
        

    }

    public void sinX(Graphics g) {
        double prec = Math.sin(sx);
        int aux, aux1;
        g.setColor(Color.BLUE);
        for (double i = sx + passo; i < dx; i += passo) {
            g.drawLine(convertiX(i - passo), convertiY(prec), convertiX(i), convertiY(Math.sin(i)));
            prec = Math.sin(i);

        }
    }

    public void cosX(Graphics g) {
        double prec = Math.cos(sx);
        int aux, aux1;
        g.setColor(Color.BLUE);
        for (double i = sx + passo; i < dx; i += passo) {
            g.drawLine(convertiX(i - passo), convertiY(prec), convertiX(i), convertiY(Math.cos(i)));
            prec = Math.cos(i);

        }
    }

    public void polX(Graphics g) {
        double prec = pol(sx);
        int aux, aux1;
        gradoPol=vettPol.size()-1;
        g.setColor(Color.BLUE);
        for (double i = sx + passo; i < dx; i += passo) {
            g.drawLine(convertiX(i - passo), convertiY(prec), convertiX(i), convertiY(pol(i)));
            prec = pol(i);

        }
    }

    public void disegnaRadici(Graphics g) {
        for (int i = 0; i < vettRadici.size(); i++) {
            g.setColor(Color.RED);
            g.fillOval(convertiX(vettRadici.get(i))-3, (getHeight() / 2)-3, 6, 6);
        }
    }

    public double pol(double x) {
        double ris = 0;

        for (int i = 0; i <= gradoPol; i++) {
            ris = ris + (Math.pow(x, gradoPol - i) * vettPol.get(i));
        }

        return ris;
    }

    public void bisezione(){
        
        double ris1=0,ris2=0,x=0,y=0;
         double x2=0,xy=0,extS,extD;
         for(double i=sx;i<dx;i+=passo){
             ris1=0;
                 ris2=0;
          switch(funz){
              case 0:
                    ris1=(double)Math.sin(i);
                    ris2=(double)Math.sin(i+passo);
                  break;
              case 1:
                  ris1=(double)Math.cos(i);
                    ris2=(double)Math.cos(i+passo);
                  break;
              case 2:
                  ris1=pol(i);
                  ris2=pol(i+passo);
                  break;
         }
          if(ris1==0){
              vettRadici.add(ris1);
              ris1+=0.001;
          }
          if(ris2==0){
              vettRadici.add(ris2);
              ris2+=0.001;
          }
          if(ris1*ris2<=0){
              nradici+=1;
                 extS=i;
                 extD=i+passo;
                 if(extD<=0&&extS<0){
                     x=(double)(extS-extD)/2;
                     while(Math.abs(x)>approx){
                            //Nuova X di mezzo//
                            x2=(double)(extS+extD)/2;
                            switch(funz){
                                case 0:
                                    xy=(double)Math.sin(x2);
                                    break;
                                case 1:
                                    xy=(double)Math.cos(x2);
                                    break;
                                case 2:
                                    xy=(double)pol(x2);
                                    break;
                            }
                            if(xy*ris1<=0){
                                extD=x2;
                                x=(double)(extS-x2)/2;
                            }
                            if(xy*ris2<=0){
                                extS=x2;
                                x=(double)(x2-extD)/2;
                            }
                     }
                     vettRadici.add(x2);
                 }
                 if(extD>0&&extS>=0){
                     x=(double)(extD-extS)/2;
                     while(Math.abs(x)>approx){
                            x2=(double)(extS+extD)/2;
                            switch(funz){
                                case 0:
                                xy=(double)Math.sin(x2);
                                    break;
                                case 1:
                                xy=(double)Math.cos(x2);
                                    break;
                                case 2:
                                xy=(double)pol(x2);
                                    break;
                            }
                            
                            if(xy*ris1<=0){
                                extD=x2;
                                x=(double)(extS-x2)/2;
                            }
                            if(xy*ris2<=0){
                                extS=x2;
                                x=(double)(x2-extD)/2;
                            }
                     }
                     vettRadici.add(x2);
                 }
            }
        }
        switch(funz){
            case 0:
                xy=(double)Math.sin(0);
                break;
            case 1:
                xy=(double)Math.cos(0);
                break;
            case 2:
                xy=(double)pol(0);
                break;
        }
        if(xy==0)
            vettRadici.add(0.0);
    }

    public int convertiX(double x) {
        if(sx<0&&dx>0){
            return (int) (((x) / unitax)+asse);
        }
        return (int)Math.abs(((x) / unitax));
    }

    public int convertiY(double y) {
        return (int) (getHeight()-((y + 1) * unitay));
    }
}