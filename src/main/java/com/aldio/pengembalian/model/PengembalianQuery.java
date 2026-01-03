package com.aldio.pengembalian.model;

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
@Document(collection = "pengembalian")
public class PengembalianQuery implements Serializable{
    @Id
    private String id;
    private String tanggal_dikembalikan;
    private String terlambat;
    private String denda;
    private String peminjamanId;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
    private LocalDateTime processedAt;

    public PengembalianQuery(PengembalianCommand command){
        this.id = command.getId();
        this.tanggal_dikembalikan = command.getTanggal_dikembalikan();
        this.terlambat = command.getTerlambat();
        this.denda = command.getDenda();
        this.peminjamanId = command.getPeminjamanId();
    }
}
