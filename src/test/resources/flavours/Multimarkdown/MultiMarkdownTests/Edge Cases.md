latex input:	mmd-article-header  
Title:	Edge Cases  
latex input:	mmd-article-begin-doc  
latex footer:	mmd-memoir-footer  



# Strong and Emph #

The ***quick*** brown ***fox*** jumped

The ***quick*** brown fox jumped

The ***quick** brown fox* jumped

The ***quick* brown fox** jumped

The ***quick* brown *fox*** jumped

The ***quick** brown **fox*** jumped

This *should* be parsed --  fo***o


# Can fail to process quickly #

*a
*a
*a
*a
*a
*a
*a
*a
*a
*a
*a
*a
*a
*a
*a
*a
*a
*a
*a
*a
*a
aaaaa


# Citations within Footnotes #

Test[][#Citekey;]. Test2 [#Citekey;]. Test3.[^cf1] Test4.[^cf2]

[^cf1]: [#Citekey;]. A

[^cf2]: [][#Citekey;]. B


[#Citekey;]:	This is an article