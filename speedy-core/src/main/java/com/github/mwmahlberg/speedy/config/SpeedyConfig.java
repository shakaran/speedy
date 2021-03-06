/*
 * Copyright 2014 Markus W Mahlberg
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package com.github.mwmahlberg.speedy.config;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.Path;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.github.mwmahlberg.speedy.Service;
import com.github.mwmahlberg.speedy.handler.DefaultWebApplicationExceptionHandler;
import com.github.mwmahlberg.speedy.handler.NotFoundExceptionHandler;
import com.github.mwmahlberg.speedy.handler.ParamExceptionHandler;
import com.github.mwmahlberg.speedy.provider.JacksonJaxbJsonProviderProvider;
import com.github.mwmahlberg.speedy.provider.ObjectMapperProvider;
import com.google.inject.Scopes;
import com.sun.jersey.api.container.ContainerException;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

public class SpeedyConfig extends JerseyServletModule {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	String basePackage;
	
	public SpeedyConfig(String basePackage) {
		
		if(basePackage == null) {
			throw new IllegalArgumentException("basePackage must not be null");
		}
		
		this.basePackage = basePackage;
	}



	@Override
	protected void configureServlets() {
		
		bind(ObjectMapper.class).toProvider(ObjectMapperProvider.class).in(Scopes.SINGLETON);
		bind(JacksonJaxbJsonProvider.class).toProvider(JacksonJaxbJsonProviderProvider.class).in(Scopes.SINGLETON);
		
		bind(GuiceContainer.class);
		
		Reflections reflection = new Reflections(basePackage);
		
		Set<Class<?>> components = new HashSet<Class<?>>();
		
		Set<Class<?>> controllers = reflection.getTypesAnnotatedWith(Path.class);
		logger.info("Found {} controllers",controllers.size());
		components.addAll(controllers);
		
		Set<Class<?>> services = reflection.getTypesAnnotatedWith(Service.class);
		logger.info("Found {} service",services.size());
		components.addAll(services);

		for (Class<?> component : components) {
			bind(component);
		}
		
		bind(NotFoundExceptionHandler.class);
		bind(DefaultWebApplicationExceptionHandler.class);
		bind(ParamExceptionHandler.class);
		
        Map<String, String> params = new HashMap<String, String>();
        params.put("com.sun.jersey.api.json.POJOMappingFeature", "true");
		serve("/*").with(GuiceContainer.class,params);
	}

}