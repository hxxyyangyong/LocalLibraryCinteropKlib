# LocalLibraryCinteropKlib


- FA(AAA.h)->FB(BBB.h)
- AAA.h code has "import "BBB.h""
- CreateLocalLib is a create FA.framework、FB.framework
- testKlib Module -> FA.def、FB.def -> FA.klib、FB.klib
- iOSApp use testKlib build Products testKlib.framework

only FA  is Successed

open FB  is Failed



