package com.code_intelligence.automotivetest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class RemoteCodeExecution {
    public static class Book implements Serializable {
        private static final long serialVersionUID = 123456789L;
        public String title;
        public String author;

        public Book(String title, String author) {
            title = title;
            author = author;
        }

        private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
            title = (String) ois.readObject();
            author = (String) ois.readObject();
        }
    }

    public static Book deserialize(ByteArrayInputStream stream) throws IOException {
        ObjectInputStream ois = new ObjectInputStream(stream);
        try {
            // Casting the result of readObject() occurs after the deserialization process ends
            // which make it possible to read any object and can lead to gadget chain attacks
            Object o = ois.readObject();
            o.toString();
            return (Book) o;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
