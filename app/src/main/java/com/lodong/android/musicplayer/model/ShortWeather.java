package com.lodong.android.musicplayer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class ShortWeather implements Serializable {
    @Expose
    @SerializedName("response")
    private Response response;

    public static class Response implements Serializable {
        @Expose
        @SerializedName("body")
        private Body body;
        @Expose
        @SerializedName("header")
        private Header header;

        public Body getBody() {
            return body;
        }

        public Header getHeader() {
            return header;
        }

        @Override
        public String toString() {
            return "Response{" +
                    "body=" + body +
                    ", header=" + header +
                    '}';
        }
    }

    public static class Body implements Serializable{
        @Expose
        @SerializedName("numOfRows")
        private int numOfRows;
        @Expose
        @SerializedName("pageNo")
        private int pageNo;
        @Expose
        @SerializedName("totalCount")
        private int totalCount;
        @Expose
        @SerializedName("items")
        private Items items;
        @Expose
        @SerializedName("dataType")
        private String dataType;

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public int getNumOfRows() {
            return numOfRows;
        }

        public void setNumOfRows(int numOfRows) {
            this.numOfRows = numOfRows;
        }

        public int getPageNo() {
            return pageNo;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public Items getItems() {
            return items;
        }

        public void setItems(Items items) {
            this.items = items;
        }

        public String getDataType() {
            return dataType;
        }

        public void setDataType(String dataType) {
            this.dataType = dataType;
        }
    }

    public static class Items implements Serializable{
        @Expose
        @SerializedName("item")
        private List<Item> item;

        public List<Item> getItem(){
            return item;
        }

        @Override
        public String toString() {
            return "Items{" +
                    "item=" + item +
                    '}';
        }
    }

    public static class Item implements Serializable{
        @Expose
        @SerializedName("ny")
        private int ny;
        @Expose
        @SerializedName("nx")
        private int nx;
        @Expose
        @SerializedName("fcstValue")
        private String fcstValue;
        @Expose
        @SerializedName("fcstTime")
        private String fcstTime;
        @Expose
        @SerializedName("FcstDate")
        private String fcstDate;
        @Expose
        @SerializedName("category")
        private String category;
        @Expose
        @SerializedName("baseTime")
        private String baseTime;
        @Expose
        @SerializedName("baseDate")
        private String baseDate;

        @Override
        public String toString() {
            return "Item{" +
                    "ny=" + ny +
                    ", nx=" + nx +
                    ", fcstValue='" + fcstValue + '\'' +
                    ", fcstTime='" + fcstTime + '\'' +
                    ", fcstDate='" + fcstDate + '\'' +
                    ", category='" + category + '\'' +
                    ", baseTime='" + baseTime + '\'' +
                    ", baseDate='" + baseDate + '\'' +
                    '}';
        }
    }

    public static class Header implements Serializable{
        @Expose
        @SerializedName("resultCode")
        private String resultCode;
        @Expose
        @SerializedName("resultMsg")
        private String resultMsg;
        public String getResultMsg() {
            return resultMsg;
        }

        public void setResultMsg(String resultMsg) {
            this.resultMsg = resultMsg;
        }

        public String getResultCode() {
            return resultCode;
        }

        public void setResultCode(String resultCode) {
            this.resultCode = resultCode;
        }

        @Override
        public String toString() {
            return "Header{" +
                    "resultMsg='" + resultMsg + '\'' +
                    ", resultCode='" + resultCode + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ShortWeather{" +
                "response=" + response +
                '}';
    }
}
