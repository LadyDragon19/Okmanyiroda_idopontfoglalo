package com.example.okmanyiroda;

public class AppointmentItem {
        private String id;
        private String email;
        private String igazolvanyTipus;
        private String idopont;

        public AppointmentItem() {}

    public AppointmentItem(String email, String igazolvanyTipus, String idopont) {
        this.email = email;
        this.igazolvanyTipus = igazolvanyTipus;
        this.idopont = idopont;
    }

    public String getEmail() {
        return email;
    }

    public String getIgazolvanyTipus() {
        return igazolvanyTipus;
    }

    public String getIdopont() {
        return idopont;
    }

    public String _getID() {
            return id;
    }

    public void setId(String id) {
            this.id = id;
    }


    public void setIgazolvanyTipus(String igazolvanyTipus) {
        this.igazolvanyTipus = igazolvanyTipus;
    }

    public void setIdopont(String idopont) {
        this.idopont = idopont;
    }
}

