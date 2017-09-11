/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package calcoloaree;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author Sebastiano
 */
public class Grafico extends JPanel{
    
    double unitax;
    double unitay;
    int asse;
    double inc=0.1;
    
    int calc=0;
    double sx,dx;
    double base;
    double areacalc=0;
    /*
     * 0=nessun calcolo
     * 1=coda destra
     * 2=coda sinistra
     * 3=due code
     * 4=area compresa
     */
    
    public Grafico(int width, int height) {
        setSize(width, height);
        unitay = 0.42/height;
        unitax = 8.0/width;
        asse=(int)getWidth()/2;
    }
    
    public void setTipoCalcolo(int t){
        calc=t;
    }
    
    public void setSx(double s){
        sx=s;
    }
    
    public void setDx(double d){
        dx=d;
    }
    
    public void setBase(double b){
        base=b;
    }
    
    public double getArea(){
        return areacalc;
    }
    
    
    
    @Override
    public void paint(Graphics g){
        
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLACK);
        g.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
        
        g.setColor(Color.DARK_GRAY);
        int prec;
        for(double i =-4;i<=4;i+=inc){
            prec=convertiY(gauss(i));
            g.drawLine(convertiX(i), prec, convertiX(i+inc), convertiY(gauss(i+inc)));
        }
        switch(calc){
            case 0:
                break;
            case 1:
                disegnaCodaDestra(g, sx, base);
                break;
            case 2:
                disegnaCodaSinistra(g, dx, base);
                break;
            case 3:
                disegnaCodaDestra(g, dx, base);
                disegnaCodaSinistra(g, sx, base);
                break;
            case 4:
                disegnaAreaCompresa(g, base);
                break;
            
        }
        
    }
    
    public double gauss(double x){
        
        double ris;
        ris=(1/(Math.sqrt(2*Math.PI)))*(Math.pow(Math.E, (-(x*x)/2)));
        
        return ris;
        
    }
    
    public void disegnaCodaDestra(Graphics g, double ex, double base){
        //true= coda destra di ex
        //false= coda sinistra di ex
        
        double area=0;
        g.setColor(Color.blue);
        
        int ax,ay,bx,by;
        
        for(double i = ex; i < 4; i += base){
            area += (base*gauss(i));
            g.setColor(Color.blue);
            if(i<0){
                ax=convertiX(i);
            }else{
                ax=convertiX(i-base);
            }
            ay=convertiY(gauss(i));
            bx=(int)(base/unitax);
            by=convertiY(0);
            g.fillRect(ax, ay, bx, by);
            g.drawRect(ax, ay, bx, by);
        }
        areacalc=area;
}
    
    public void disegnaCodaSinistra(Graphics g, double ex, double base){
        //true= coda destra di ex
        //false= coda sinistra di ex
        
        double area=0;
        g.setColor(Color.blue);
        
        int ax,ay,bx,by;
        
        for(double i = ex; i > -4; i -= base){
            area += (base*gauss(i));
            g.setColor(Color.blue);
            if(i<0){
                ax=convertiX(i);
            }else{
                ax=convertiX(i-base);
            }
            ay=convertiY(gauss(i));
            bx=(int)(base/unitax);
            by=convertiY(0);
            g.fillRect(ax, ay, bx, by);
            g.drawRect(ax, ay, bx, by);
        }
        areacalc=area;
}
    
    public void disegnaAreaCompresa(Graphics g, double base){
        //true= coda destra di ex
        //false= coda sinistra di ex
        
        double area=0;
        g.setColor(Color.blue);
        
        int ax,ay,bx,by;
        
        for(double i = sx; i < dx; i += base){
            area += (base*gauss(i));
            g.setColor(Color.blue);
            if(i<0){
                ax=convertiX(i);
            }else{
                ax=convertiX(i-base);
            }
            ay=convertiY(gauss(i));
            bx=(int)(base/unitax);
            by=convertiY(0);
            g.fillRect(ax, ay, bx, by);
            g.drawRect(ax, ay, bx, by);
        }
        areacalc=area;
}
    
    public double calcolaCodaDestra(){
        double area=0;
        for(double i = sx; i < 4; i += base){
            area += (base*gauss(i));
        }
        areacalc=area;
        return area;
    }
    
    public double calcolaCodaSinistra(){
        double area=0;
        for(double i = dx; i > -4; i -= base){
            area += (base*gauss(i));
        }
        areacalc=area;
        return area;
    }
    
    public double calcolaAreaCompresa(){
        double area=0;
        for(double i = sx; i < dx; i += base){
            area += (base*gauss(i));
        }
        areacalc=area;
        return area;
    }
    
    public double calcolaCode(){
        double area=0;
        for(double i = dx; i < 4; i += base){
            area += (base*gauss(i));
        }
        for(double i = sx; i > -4; i -= base){
            area += (base*gauss(i));
        }
        areacalc=area;
        return area;
    }
    
    /*
    public double CalcolaArea(boolean metodo, double sx, double dx, double base) {
43 	//false = area compresa
44 	//true = code
45 	double area = 0;
46 	for (double i = sx; i < dx; i += base) {
47          area += (base * CalcolaGauss(i));
48 	}
49 	
50 	if (metodo) {
51 	return area;
52 	} else {
53 	return 1 - area;
54 	}
55 	
56 	}
57 	
58 	public double CalcolaCoda(boolean metodo, double ex, double base) {
59 	//true a destra di ex
60 	//false = a sinistra di ex
61 	double area = 0;
62 	for (double i = ex; i < 4; i += base) {
63 	area += (base * CalcolaGauss(i));
64 	}
65 	
66 	if (metodo) {
67 	return area;
68 	} else {
69 	return 1 - area;
70 	}
71 	
72 	}
* */
    
    public int convertiX(double x) {
        int cont=0;
        if(x<0){
            cont= (int) ((Math.abs(x) / unitax));
            cont=asse-cont;
        }
        if(x==0){
            cont=asse;
        }
        if(x>0){
            cont=(int) (((x) / unitax));
            cont=asse+cont;
        }
        return cont;
    }

    public int convertiY(double y) {
        int cont;
        cont =(int)(y / unitay);
        cont = getHeight()-cont;
        return cont;
    }
    
}
