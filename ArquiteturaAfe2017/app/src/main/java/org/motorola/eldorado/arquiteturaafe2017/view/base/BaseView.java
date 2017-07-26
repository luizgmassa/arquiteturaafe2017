package org.motorola.eldorado.arquiteturaafe2017.view.base;

/**
 * The Base View interface class.
 */
public interface BaseView<T> {

    /**
     * Sets the Presenter instance used in each view.
     *
     * @param presenter the presenter instance.
     */
    void setPresenter(T presenter);

}
