package com.aldio.anggota.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "anggota") // PERBAIKAN: Anotasi untuk MongoDB
public class AnggotaQuery implements Serializable {
    @Id
    private String id;

    private String nim;
    private String nama;
    private String alamat;
    private String jenis_kelamin;
    private LocalDateTime CreatedAt;

    public AnggotaQuery(AnggotaCommand command){
        this.id = command.getId();
        this.nim = command.getNim();
        this.nama = command.getNama();
        this.alamat = command.getAlamat();
        this.jenis_kelamin = command.getJenis_kelamin();
        this.CreatedAt = command.getCreatedAt();
    }
}