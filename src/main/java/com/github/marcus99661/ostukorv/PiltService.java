package com.github.marcus99661.ostukorv;

import org.apache.commons.codec.binary.Hex;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.MessageDigest;

@Service
public class PiltService {

    @Autowired
    public PiltRepository photoRepo;

    public String addPhoto(String title, MultipartFile file) throws IOException {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(file.getBytes(), 0, file.getBytes().length);
            Pilt photo = new Pilt(title, new Binary(BsonBinarySubType.BINARY, file.getBytes()), Hex.encodeHexString(md.digest()));
            photo = photoRepo.insert(photo);
            return photo.getId();
        } catch (Exception e) {
            System.out.println(e);
        }
        return "error";
    }

    public Pilt getPhoto(String hash) {
        return photoRepo.findByHash(hash);
    }
}