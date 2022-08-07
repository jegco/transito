package com.tesis.transito.persistencia.utils.archivos;

@FunctionalInterface
interface DropboxActionResolver<T> {

    T perform() throws Exception;

}
