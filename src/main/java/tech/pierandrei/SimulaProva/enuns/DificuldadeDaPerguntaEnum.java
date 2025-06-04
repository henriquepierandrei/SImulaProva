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

public enum DificuldadeDaPerguntaEnum {
    MUITO_FACIL("Muito fácil"),
    FACIL("Fácil"),
    MEDIO("Médio"),
    DIFICIL("Difícil"),
    MUITO_DIFICIL("Muito Difícil");

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

    // Evitar erro com o uso do Enum
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