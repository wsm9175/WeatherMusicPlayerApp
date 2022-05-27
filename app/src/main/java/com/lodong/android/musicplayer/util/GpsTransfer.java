package com.lodong.android.musicplayer.util;

public class GpsTransfer {
    private double lat;
    private double lon;

    private double xLat;
    private double yLon;

    public GpsTransfer() {}

    public GpsTransfer(double lat, double lon){
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getxLat() {
        return xLat;
    }

    public void setxLat(double xLat) {
        this.xLat = xLat;
    }

    public double getyLon() {
        return yLon;
    }

    public void setyLon(double yLon) {
        this.yLon = yLon;
    }

    @Override
    public String toString() {
        return "GpsTransfer{" +
                "lat=" + lat +
                ", lon=" + lon +
                ", xLat=" + xLat +
                ", yLon=" + yLon +
                '}';
    }

    public void transfer(GpsTransfer gpt, int mode){
        double RE = 6371.00877; //지구 반경
        double GRID = 5.0; //격자 간격
        double SLAT1 = 30.0; // 투영 위도1
        double SLAT2 = 60.0; // 투영 위도2
        double OLON = 126.0; // 기준점 경도
        double OLAT = 38.0; // 기준점 위도
        double XO = 43; //기준점 X좌표
        double YO = 136; //기준점 Y좌표

        double DEGRAD = Math.PI / 180.0;
        double RADDEG = 180.0/Math.PI;

        double re = RE/GRID;
        double slat1 = SLAT1 * DEGRAD;
        double slat2 = SLAT2 * DEGRAD;
        double olon = OLON * DEGRAD;
        double olat = OLAT * DEGRAD;

        double sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sn = Math.log(Math.cos(slat1) / Math.cos(slat2) / Math.log(sn));
        double sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sf = Math.pow(sf, sn) * Math.cos(slat1) / sn;
        double ro = Math.tan(Math.PI * 0.25 + olat * 0.5);
        ro = re * sf / Math.pow(ro, sn);

        if(mode == 0){
            double ra = Math.tan(Math.PI * 0.25 + (gpt.getLat()) * DEGRAD * 0.5);
            ra = re * sf / Math.pow(ra, sn);
            double theta = gpt.getLon() * DEGRAD - olon;
            if(theta > Math.PI) theta -= 2.0 * Math.PI;
            if(theta < -Math.PI) theta += 2.0 * Math.PI;
            theta *= sn;
            double x = Math.floor(ra * Math.sin(theta) + XO + 0.5);
            double y = Math.floor(ro - ra * Math.cos(theta) + YO + 0.5);
            gpt.setxLat(x);
            gpt.setyLon(y);
        }else {
            double xlat = gpt.getxLat();
            double ylon = gpt.getyLon();
            double xn = xlat -XO;
            double yn = ro - ylon + YO;
            double ra = Math.sqrt(xn * xn + yn * yn);
            if(sn < 0.0){
                ra = -ra;
            }
            double alat = Math.pow((re * sf / ra), (1.0 / sn));
            alat = 2.0 * Math.atan(alat) - Math.PI * 0.5;

            double theta = 0.0;
            if(Math.abs(xn) <= 0.0){
                theta = 0.0;
            }else{
                if(Math.abs(yn) <= 0.0){
                    theta = Math.PI * 0.5;
                    if(xn < 0.0){
                        theta -= theta;
                    }
                }else theta = Math.atan2(xn, yn);
            }

            double alon = theta / sn + olon;

            gpt.setLat(alat * RADDEG);
            gpt.setLon(alon * RADDEG);
        }


    }
}
