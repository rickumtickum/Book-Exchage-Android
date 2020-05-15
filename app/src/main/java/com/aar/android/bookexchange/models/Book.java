package com.aar.android.bookexchange.models;


import android.os.Parcel;
import android.os.Parcelable;


public class Book implements Parcelable {

    private String bookname;
    private String authorname;
    private String price;
    private String location;
    private String booktype;
    private String condition;
     private String exchange;
    private String photoUrl;
    private String email;
    private String uidd;
    private Integer count;

    public Book() {super();}

    public Book(String bookname,String authorname,String price,String location,String condition,String exchange,String booktype,String photoUrl, String email) {
        this.bookname=bookname;
        this.authorname=authorname;
        this.price=price;
        this.location=location;
        this.condition=condition;
        this.exchange=exchange;
        this.booktype=booktype;
        this.photoUrl=photoUrl;
        this.email=email;
        count=0;
    }

    public String getBooktype() {
        return booktype;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice() {

        return price;
    }

    public void setBooktype(String booktype) {
        this.booktype = booktype;
    }

    public void setAuthorname(String authorname) {
        this.authorname = authorname;
    }
    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getAuthorname() {

        return authorname;

    }


    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getBookname() {

        return bookname;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void increment()
    {
        this.count ++;
    }

    public String getEmail() {
        return email;
    }

    public String getUidd() {
        return uidd;
    }

    public void setUidd(String uidd) {
        this.uidd = uidd;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public String getLocation() {
        return location;
    }
    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }


    public static Creator<Book> getCreator() { return CREATOR;}

    public Book(Parcel parcel){
        this.bookname = parcel.readString();
        this.authorname = parcel.readString();
        this.price = parcel.readString();
        this.location=parcel.readString();
        this.condition=parcel.readString();
        this.exchange=parcel.readString();
        this.booktype = parcel.readString();
        this.photoUrl=parcel.readString();
        this.email=parcel.readString();
        this.uidd=parcel.readString();
        this.count=parcel.readInt();
    }


    public static final Parcelable.Creator<Book> CREATOR
            = new Parcelable.Creator<Book>() {
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        public Book[] newArray(int size) {
            return new Book[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.bookname);
        parcel.writeString(this.authorname);
        parcel.writeString(this.price);
        parcel.writeString(this.location);
        parcel.writeString(this.condition);
        parcel.writeString(this.exchange);
        parcel.writeString(this.booktype);
        parcel.writeString(this.photoUrl);
        parcel.writeString(this.email);
        parcel.writeString(this.uidd);
        parcel.writeInt(this.count);
    }

}
