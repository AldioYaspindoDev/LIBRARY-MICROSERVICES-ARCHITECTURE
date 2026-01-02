package com.aldio.peminjaman.model;

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
@Document(collection = "peminjaman")
public class PeminjamanQuery implements Serializable{
    @Id
    private String id;
    private String tanggal_pinjam;
    private String tanggal_kembali;
    private String anggotaId;
    private String bukuId;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime processedAt;

    public PeminjamanQuery(PeminjamanCommand command){
        this.id = command.getId();
        this.tanggal_pinjam = command.getTanggal_pinjam();
        this.tanggal_kembali = command.getTanggal_kembali();
        this.anggotaId = command.getAnggotaId();
        this.bukuId = command.getBukuId();
        this.createdAt = command.getCreatedAt();
        this.processedAt = command.getProcessedAt();
    }
}