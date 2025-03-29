//package biz.dss.ticketbookingsystem;
//
//public class GraphPoint {
//    private int x;
//    private int y;
//
//    private GraphPoint(GraphPointBuilder graphPointBuilder){
//        this.x = graphPointBuilder.x;
//        this.y = graphPointBuilder.y;
//    }
//
//    public int getX() {
//        return x;
//    }
//
//    public void setX(int x) {
//        this.x = x;
//    }
//
//    public int getY() {
//        return y;
//    }
//
//    public void setY(int y) {
//        this.y = y;
//    }
//
//    public static class GraphPointBuilder{
//        private int x;
//        private int y;
//
//        public GraphPointBuilder setX(int x){
//            this.x = x;
//            return this;
//        }
//
//        public GraphPointBuilder setY(int y){
//            this.y = y;
//            return this;
//        }
//
//        public GraphPoint build(){
//            return new GraphPoint(this);
//        }
//    }
//}
