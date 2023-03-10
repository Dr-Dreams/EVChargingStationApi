package com.ev.charging.station.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Station {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stationId;


    @Getter
    @Setter
    @NonNull
    @Size(max = 100)
    @NotNull
    private String stationName;

    @Setter
    @Getter
    @NonNull
    private Double stationPricing;


    @Setter
    @Getter
    @NonNull
    @Size(max = 200)
    private String stationAddress;

    @Getter
    @Setter
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] stationImage;
}
