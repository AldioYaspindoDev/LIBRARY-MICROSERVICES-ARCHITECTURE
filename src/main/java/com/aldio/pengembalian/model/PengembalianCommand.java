package com.aldio.pengembalian.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pengembalian")
public class PengembalianCommand implements Serializable{
    @Id
    private String id;
    private String tanggal_dikembalikan;
    private String terlambat;
    private String denda;
    private String peminjamanId;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
    private LocalDateTime processedAt;

    @Transient
    private EventType eventType;

    public enum EventType {
        CREATED,
        UPDATED,
        DELETED
    }
}
