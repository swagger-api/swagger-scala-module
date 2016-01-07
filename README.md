# Swagger Scala Module

[![Build Status](https://travis-ci.org/swagger-api/swagger-scala-module.svg?branch=develop)](https://travis-ci.org/swagger-api/swagger-scala-module)

The goal of Swagger™ is to define a standard, language-agnostic interface to REST APIs which allows both humans and computers to discover and understand the capabilities of the service without access to source code, documentation, or through network traffic inspection. When properly defined via Swagger, a consumer can understand and interact with the remote service with a minimal amount of implementation logic. Similar to what interfaces have done for lower-level programming, Swagger removes the guesswork in calling the service.

Swagger-scala-module is a pure scala module for use with the swagger framework.

Check out [Swagger-Spec](https://github.com/swagger-api/swagger-spec) for additional information about the Swagger project, including additional libraries with support for SpringMVC, other languages and more. 

## See the Wiki!
The [github wiki](https://github.com/swagger-api/swagger-core/wiki) contains documentation, samples, etc.  Start there.

## Compatibility
This project is compatible with [swagger-core](https://github.com/swagger-api/swagger-core) 1.5.x or greater.

## Usage
To enable the swagger-scala-module, include the appropriate version in your project:

```
  "io.swagger" %% "swagger-scala-module" % "1.0.0",
```

Which will include the proper cross-publish version of swagger-scala-module.


## How does it work?
Including the library in your project allows the swagger extension module to discover this module, bringing in the appropriate jackson library in the process.  You can then use scala classes and objects in your swagger project.

## Support
The following methods are available to obtain support for Swagger:

- [The Swagger Google Group](https://groups.google.com/forum/#!forum/swagger-swaggersocket) - This would normally be your first stop to get support for Swagger. Here you can find previously asked question, and ask new ones. When asking a question, please provide as much information as you can regarding the environment you use (development language, library, versions.
- The [Issues tab](https://github.com/swagger-api/swagger-scala-module/issues?state=open) - Please open feature requests and bugs here. If you're not sure you encountered a bug, or if it's a general usage question, please use the Google Group mentioned above.
- IRC! you can find us on [freenode](http://webchat.freenode.net/?channels=swagger) in the channel #Swagger. You can talk with us directly there.


## Get started with Swagger!
See the guide on [getting started with swagger](http://swagger.io) to get started with adding swagger to your API.


### To build from source (currently 1.0.0)
```
sbt publishLocal
```

License
-------

Copyright 2016 SmartBear Software, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at [apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
