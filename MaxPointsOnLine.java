import java.util.*;

class Point {
    int x;
    int y;
    Point() { x = 0; y = 0; }
    Point(int a, int b) { x = a; y = b; }
}

class MaxPointsOnLine {
    private Map<LinkedList<Integer>,Integer> s = new HashMap<>();

    public int maxPoints(Point[] points) {
        if(points.length==0)
            return 0;
        if(points.length==1)
            return 1;
        if(points.length==2)
            return 2;
        for(int i=0;i<points.length-1;i++){
            for(int j=i+1;j<points.length;j++){
                LinkedList<Integer> l = find_line(points[i],points[j]);
                if(l!=null){
                    Integer val = s.get(l);
                    if(val == null){
                        s.put(l,1);
                    }
                    else{
                        s.put(l,val+1);
                    }
                }
            }
        }

        if(s.size()==0){
            return points.length;
        }
        Iterator it = s.entrySet().iterator();
        LinkedList<Integer> maxLine = null;
        int maxPoints =0;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if(((int)pair.getValue())>maxPoints){
                maxLine = (LinkedList<Integer>) pair.getKey();
                maxPoints =(int)pair.getValue();
            }
        }
        return solveLine(maxLine,points);
    }

    public int solveLine(LinkedList<Integer> l, Point[] points){
        int count=0;
        int q = l.get(0);
        int p = l.get(1);
        int b = l.get(2);
        for(int i=0;i<points.length;i++){
            if( q*(points[i].y) == p*(points[i].x)+b )
                count++;
        }
        return count;
    }

    public int GCD(int a, int b) {
        if (b==0) return a;
        return GCD(b,a%b);
    }
    public LinkedList<Integer> find_line(Point p1,Point p2){
        int p = p2.y - p1.y;
        int q = p2.x - p1.x;
        int bu = (p1.y *q) - (p1.x * p);

        if(q==0 && p ==0 && bu ==0)
            return null;

        int gcd = GCD(q,p);
        gcd = GCD(gcd,bu);
        int div = gcd;
        if(div==0)
            div =1;

        LinkedList<Integer> l = new LinkedList<>();
        l.add(q/div);
        l.add(p/div);
        l.add(bu/div);
        //System.out.println(l);
        return l;
    }
}

class TesterMaxPointsOnLine{
    public static void main(String args[]){
        Point points[] = new Point[9];
        points[0] = new Point(84,250);
        points[1] = new Point(0,0);
        points[2] = new Point(1,0);
        points[3] = new Point(0,-70);
        points[4] = new Point(1,-1);
        points[5] = new Point(0,-70);
        points[6] = new Point(21,10);
        points[7] = new Point(42,90);
        points[8] = new Point(-42,-230);
        //System.out.println(new MaxPointsOnLine().find_line(new Point(1,1),new Point(3,4)));
        System.out.println(new MaxPointsOnLine().maxPoints(points));

    }
}
