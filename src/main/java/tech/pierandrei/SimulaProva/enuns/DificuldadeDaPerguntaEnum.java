/*
 * Copyright (c) 2025 SimulaProva
 * Autor: Henrique Pierandrei
 * Este código é propriedade de SimulaProva
 * Todos os direitos reservados.
 * É proibida a reprodução, distribuição ou uso não autorizado
 * deste código sem permissão explícita por escrito.
 */

package tech.pierandrei.SimulaProva.enuns;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * ENUM da dificuldade das perguntas
 */
public enum DificuldadeDaPerguntaEnum {
    FACIL("Fácil"),
    MEDIO("Médio"),
    DIFICIL("Difícil");

    private final String displayName;

    DificuldadeDaPerguntaEnum(String displayName) {
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return this.displayName;
    }

    @Override
    public String toString() {
        return this.displayName;
    }

    /**
     * Evitar possíveis erros com o uso do Enum.
     * @param value - Valor obtido do Enum
     * @return - Retorna o valor caso o Enum inserido pelo usuário existe.
     */
    @JsonCreator
    public static DificuldadeDaPerguntaEnum fromString(String value) {
        // Primeiro tenta pelo nome do enum
        for (DificuldadeDaPerguntaEnum diff : values()) {
            if (diff.name().equalsIgnoreCase(value)) {
                return diff;
            }
        }

        // Depois tenta pelo display name
        for (DificuldadeDaPerguntaEnum diff : values()) {
            if (diff.displayName.equalsIgnoreCase(value)) {
                return diff;
            }
        }

        throw new IllegalArgumentException("Dificuldade inválida: " + value);
    }
}