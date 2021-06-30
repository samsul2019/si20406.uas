package uas.alfifadilahhardianto.alfiuasfirebase.paket;

public class Dataku {
    String Kunci;
    String Isi;

    public Dataku(){

    }

    public Dataku(String kunci, String isi) {
        Kunci = kunci;
        Isi = isi;

    }

    public String getKunci() {
        return Kunci;
    }

    public void setKunci(String kunci) {
        Kunci = kunci;
    }

    public String getIsi() {
        return Isi;
    }

    public void setIsi(String isi) {
        Isi = isi;
    }
}
