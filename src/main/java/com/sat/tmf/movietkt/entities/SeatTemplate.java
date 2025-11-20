package com.sat.tmf.movietkt.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "seat_templates")
public class SeatTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;
    @Column(name = "seat_rows")
    private Integer rows;
    @Column(name = "seat_cols")
    private Integer cols;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screen_id", nullable = false)
    private Screen screen;

    @OneToMany(mappedBy = "seatTemplate", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    private List<TemplateSeat> seats;

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getRows() { return rows; }
    public void setRows(Integer rows) { this.rows =rows; }

    public Integer getCols() { return cols; }
    public void setCols(Integer cols) { this.cols = cols; }

    public Screen getScreen() { return screen; }
    public void setScreen(Screen screen) { this.screen = screen; }

    public List<TemplateSeat> getSeats() { return seats; }
    public void setSeats(List<TemplateSeat> seats) { this.seats = seats; }
}


