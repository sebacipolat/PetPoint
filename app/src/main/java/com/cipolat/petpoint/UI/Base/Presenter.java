package com.cipolat.petpoint.UI.Base;



public interface Presenter <V>{

    void setView(V view);

    void detachView();
}
