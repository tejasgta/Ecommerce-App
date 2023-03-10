package com.avenger.ecommerce.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.avenger.ecommerce.entity.Country;
import com.avenger.ecommerce.entity.Product;
import com.avenger.ecommerce.entity.ProductCategory;
import com.avenger.ecommerce.entity.State;


@Configuration
public class MyRestConfig implements RepositoryRestConfigurer {
	
	private EntityManager entityManager;
	
	public MyRestConfig(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
		
		HttpMethod unsupportedmethods[] = {HttpMethod.PUT,HttpMethod.POST,HttpMethod.DELETE};
		
		disableHttpMethods(Product.class,config, unsupportedmethods);
		disableHttpMethods(ProductCategory.class,config, unsupportedmethods);
		disableHttpMethods(Country.class,config, unsupportedmethods);
		disableHttpMethods(State.class,config, unsupportedmethods);
		
		exposeIds(config);
	}

	private void disableHttpMethods(Class theclass,RepositoryRestConfiguration config, HttpMethod[] unsupportedmethods) {
		config.getExposureConfiguration()
		.forDomainType(theclass)
		.withItemExposure((metadata, httpmethods) -> httpmethods.disable(unsupportedmethods))
		.withCollectionExposure((metadata, httpmethods) -> httpmethods.disable(unsupportedmethods));
	}
	
	private void exposeIds(RepositoryRestConfiguration config) {

        // expose entity ids
        //

        // - get a list of all entity classes from the entity manager
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

        // - create an array of the entity types
        List<Class> entityClasses = new ArrayList<>();

        // - get the entity types for the entities
        for (EntityType tempEntityType : entities) {
            entityClasses.add(tempEntityType.getJavaType());
        }

        // - expose the entity ids for the array of entity/domain types
        Class[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);
    }
	

}
