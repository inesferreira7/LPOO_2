package com.tq.doodle.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

/**
 * Created by Inês on 05/06/2016.
 */
public class FileHandler {

    FileHandle file;

    public FileHandler() {

        file = Gdx.files.local("file.txt");

    }

    public void write() {
        if (file.exists()) System.out.println("Ja ta criado!!");
        String text = file.readString();
        System.out.println(text);

        try {
            if (!file.exists()) {
                file.file().createNewFile();
                System.out.println("Criou xó agora");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Deu merda!");
        }

        //Escrever para o ficheiro - por noutra funcao
        file.writeString("blab la blab", false);
    }
}
