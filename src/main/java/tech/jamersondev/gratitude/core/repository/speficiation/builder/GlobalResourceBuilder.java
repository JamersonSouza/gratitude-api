package tech.jamersondev.gratitude.core.repository.speficiation.builder;

import org.springframework.data.jpa.domain.Specification;

public abstract class GlobalResourceBuilder<T> {
    protected Specification<T> specification;

    protected GlobalResourceBuilder(Specification<T> baseSpec){
        this.specification = Specification.where(baseSpec);
    }

    public GlobalResourceBuilder<T> with(Specification<T> spec){
        if(spec != null){
            this.specification = this.specification.and(spec);
        }
        return this;
    }

    public Specification<T> build() {
        return this.specification;
    }

}
