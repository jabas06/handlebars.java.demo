# Simple console app to render `handlebars.java` templates

A list of all helpers that can be used in templates is available [here](https://javadoc.io/static/com.github.jknack/handlebars/4.1.2/com/github/jknack/handlebars/helper/package-summary.html).

In order for Helpers to be resolved, they need to be registered via `Handlebars.registerHelpers` method. See [App.java](src/main/java/handlebars/java/demo/App.java) and add more helpers as needed.

## Run

1. Put your templates and json payloads within the `templates` folder.
2. Edit the [application.properties](src/main/resources/application.properties) and specify the template and payload file you want to render.

```
templatesPath=templates
templateFile=template1.hbs
payloadFile=template1.json
outFile=renderedTemplate.html
```

3. If you're using VSCode and Java plugin, just press F5 👌.
4. The rendered file will be created with name equal to `outFile` inside `templatesPath` folder.
