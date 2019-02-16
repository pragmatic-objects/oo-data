# OO-Data

[![Build Status (Travis)](https://img.shields.io/travis/pragmatic-objects/oo-data/master.svg)](https://travis-ci.org/pragmatic-objects/oo-data)
[![Build status (AppVeyor)](https://ci.appveyor.com/api/projects/status/6vv95ed3t1km960l?svg=true)](https://ci.appveyor.com/project/skapral/oo-data)
[![Codecov](https://codecov.io/gh/pragmatic-objects/oo-data/branch/master/graph/badge.svg)](https://codecov.io/gh/pragmatic-objects/oo-data)

Tool for generating data-like interfaces with basic structural subtyping support.

## Quick start

1. Add Maven dependency:

```
<dependency>
    <groupId>com.pragmaticobjects.oo.data</groupId>
    <artifactId>data-alltogether</artifactId>
    <version>0.0.0-SNAPSHOT</version>
</dependency>
```

2. Describe your data by annotating `package-info.java`:

```
@Scalar(value = "UserName", type = String.class)
@Scalar(value = "UserAvatar", type = URI.class)
@Scalar(value = "UserLocation", type = String.class)
@Structure(value = "UserVisualInfo", has = {"UserName", "UserAvatar"})
@Structure(value = "UserContactInfo", has = {"UserName", "UserLocation"})
@Structure(value = "UserFullInfo", has = {"UserName", "UserAvatar", "UserLocation"})
package your.package.name;
```

3. Build the project. OO-Data processes annotations on build phase and generates:
- interfaces for your data: `UserName`, `UserAvatar`, `UserLocation`, `UserVisualInfo`, `UserContactInfo`, `UserFullInfo`
- value objects: `UserNameValue`, `UserAvatarValue`, etc.
- composite objects: `UserVisualInfoComposite`, `UserContactInfoComposite`, etc.

4. Have fun

## What sort of fun?

1. Structures are always subtypes from scalars they have inside:

```
UserVisualInfo userInfo = ...;
UserName name = userInfo; // correct
UserAvatar avatar = userInfo; // correct
UserLocation loc = userInfo; // incorrect! Location is not visual information.
```

2. You can substitute larger structures in places where smaller ones are required:

```
UserFullInfo userFullInfo = ...;
UserContactInfo info = userFullInfo; // full information includes contact information, it's okay
```

3. You can compose structures from scalars and other structures:

```
// from separate scalars...
UserFullInfo info = new UserFullInfoComposite(
    new UserNameValue("Sergey Kapralov"),
    new UserAvatarValue(url),
    new UserLocation("Nizhniy Novgorod, Russia")
);
// Or even from other structures
UserVisualInfo visualInfo = new UserVisualInfoValue("Sergey Kapralov", avatarUrl);
UserContactInfo contactInfo = new UserContactInfo("Sergey Kapralov", "Nizhniy Novgorod, Russia");
UserFullInfo fullInfo = new UserFullInfoComposite(
    new UserNameValue("Kapralov Sergey"),
    visualInfo, // avatar will be taken from visualInfo
    contactInfo // location - from contactInfo
);
```

4. More tricks to come later...
