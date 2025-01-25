package com.alifetvaci.travelmanagementservice.repository.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;


@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TRANSPORTATION")
@Entity
public class Transportation extends BaseEntity {

    @ManyToOne
    private Location originLocation;

    @ManyToOne
    private Location destinationLocation;

    @Enumerated(EnumType.STRING)
    private TransportationType transportationType; // FLIGHT, BUS, SUBWAY, UBER

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Integer> operatingDays; // [1, 3, 5, 6] (for Monday, Wednesday, Friday, Saturday)

}
