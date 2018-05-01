package upec.projetandroid2017_2018;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class Dessin extends View {

    ArrayList<Point> points = new ArrayList<>();
    NotifyActivity activity;
    private int couleur = Color.BLACK;
    private int epaisseur = 10;


    public Dessin(Context context, AttributeSet attrs) {
        super(context,attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint p = new Paint();
        for(int i = 0; i < points.size(); i++){
            p.setColor(points.get(i).getCouleur());
            canvas.drawCircle(points.get(i).getX(),points.get(i).getY(),points.get(i).getEpaissaeur(),p);
        }
    }

    public int getCouleur() {
        return couleur;
    }

    public int getEpaisseur() {
        return epaisseur;
    }

    public void setCouleur(int couleur) {
        this.couleur = couleur;
    }

    public void setEpaisseur(int epaisseur) {
        this.epaisseur = epaisseur;
    }

    public void setActivity (NotifyActivity activity){
        this.activity = activity;
    }

    public void addPoint(Point p){
        points.add(p);
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Point p = new Point((int) event.getX(),(int) event.getY(),couleur,epaisseur);
        points.add(p);
        activity.sendData(p.toSendTo());
        invalidate();
        return true;
    }
}