package upec.projetandroid2017_2018;

public class Point {

    private int x;
    private int y;
    private int couleur;
    private int epaissaeur;

    public Point(int x, int y, int couleur, int epaisseur){
        this.x = x;
        this.y = y;
        this.couleur = couleur;
        this.epaissaeur = epaisseur;
    }

    public int getX(){
        return x;
    }

    public int getY() {
        return y;
    }

    public int getCouleur() {
        return couleur;
    }

    public String toSendTo(){
        return x +"\n"+   y   +"\n"+   couleur  +"\n"+   epaissaeur  +"\n";
    }

    public int getEpaissaeur() {
        return epaissaeur;
    }

    public static Point createPoint(String s){
        String lines[] = s.split("\\r?\\n");
        int X=Integer.parseInt(lines[0]);
        int Y=Integer.parseInt(lines[1]);
        int C=Integer.parseInt(lines[2]);
        int E=Integer.parseInt(lines[3]);
        return new Point(X,Y,C,E);
    }
}
