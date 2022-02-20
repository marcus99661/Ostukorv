package com.github.marcus99661.ostukorv.Data;

import java.time.LocalDateTime;

public class Allahindlus {
    public LocalDateTime algus;
    public LocalDateTime lopp;
    public float allaProtsent;

    public Allahindlus(LocalDateTime algus, LocalDateTime lopp, float allaProtsent) {
        this.algus = algus;
        this.lopp = lopp;
        this.allaProtsent = allaProtsent;
    }
}
