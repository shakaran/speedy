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

package com.github.mwmahlberg.speedy.template;

import com.github.mwmahlberg.speedy.TemplateEngine;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class ThymeleafModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(TemplateEngine.class).to(ThymeleafTemplateEngine.class).in(Scopes.SINGLETON);;
	}

}