package it.iwkz.api.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "incomes")
public class Income extends AuditDate{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private Double amount;

    @NotNull
    private int month;

    @NotNull
    private int year;

    private String info;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "income_type_id", nullable = false)
    private IncomeType incomeType;
}
