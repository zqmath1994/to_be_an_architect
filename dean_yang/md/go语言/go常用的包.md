#strings
* strings.Split(names,"."):分隔字符串，以.为分隔符
* strings.SplitAfter():保留分隔符
* strings.SplitN()：指定分隔的次数，从左到右
* strings.SplitAfterN():指定分隔次数
* strings.FiledsFunc():函数有两个参数，一个字符串，一个函数func（char rune） bool 函数的引用，如果返回的是true则进行切分，swith函数判断
* strings.Replace():可以将一个字符串中出现的某个字符串全部替换，例如：strings.Replace(names,"\t","",-1)-1表示替换的次数没有限制
* strings.Contains(s,t) 如果t在s中则返回true
* strings.Count(s,t) t在s中出现的次数
* strings.EqualFold（s，t）如果字符串相等话则返回true，注意次函数比较要区分大小写
* strings.Fields(s) 在字符串空白处进行切分，返回字符串切片
* strings.FieldsFunc(s,f) 按照f函数返回结果切分，如果f返回true，就在那个字符串进行切分
* strings.HasPrefix(s,t) 如果字符串s是以t开头的则返回true
* strings.HasSuffix(s,t) 如果字符串s是以t结尾的则返回true
* strings.Index(s,t) t在s中第一次出现的索引位置
* strings.IndexFunc(s,t) s中第一次令f函数返回true的字符的索引位置
* strings.IndexAny(s,t) s中第一个出现在t中的字符的索引位置
* string.Join(xs,t) 将xs中所有字符串按照t分隔符进行合并（t可能为“”）
* strings.LastIndex(s,t) t在s中最后一次出现的位置
* strings.LastIndexAny(s,t) s中最后一个出现在t中的字符索引位置
* strings.LastIndexFunc(s,f) s中最后一个返回true的字符的索引位置
* strings.Map(mf,t)	按照mf函数规则（func(rune) rune）替换t中所有对应的字符
* strings.NewReader(s) 创建一个字符串s的对象，支持Read(),ReadByte()和ReadRune（）方法
* strings.NewReplacer(...) 创建一个替换器能够处理多对旧新字符串的替换
* strings.Repeat(s,i) 重复i次字符串s
* strings.Replace(s,old,new,i) 返回一个新的字符串，对s中旧的非重叠字符串用新的字符串替换，执行i次替换操作，如果i = -1 则全部替换
* strings.Title(s) 返回一个新的字符串，对原字符串中每一个单词进行标题首字符大写处理
* strings.Tolower(s) 返回一个新的字符串，对原 s进行小写转换
* strings.ToLowerSpecial(r,s) 返回一个新的字符串，按照指定的有限规则对原s中的相应的unicode字母进行小写转换		r是unicode类型的，SpecialCase是用来指定unicode规则的
* strings.ToTitle(s) 返回一个新的字符串，对原s进行标题格式转换
* strings.ToUpper(s) 返回一个新的字符串，对原s中所有的字母进行大写转换处理
* strings.Trim(s,t) 饭会一个新的字符串，从s两段过滤掉t
* strings.Trim
* Func(s,f) 返回一个新的字符串，从s两段开始过滤掉f返回true的每一个字符
* strings.TrimLeft(s,f) 返回一个新的字符串，从s左边开始过滤掉t
* strings.TrimLeftFunc(s,f) 返回一个新的字符串，从s左边开始过滤掉f返回true的每一个字符
* strings.TrimRight(s,t) 返回一个新的字符串。从s右边开始过滤掉t
* strings.TrimSpace(s) 返回一个新的字符串，从s左右两段开始过滤掉空格
* strings.TrimRightFunc(s,f) 返回一个新的字符串，从s右边开始过滤掉f返回true的每一个字符

#strconv
提供了许多可以再字符串和其他类型的数据之间进行转换的函数
参数bs是一个[]byte切片，base是一个进制单位（2~36）,bits是指其结果必须满足比特数（int型而言，可以是8 16 32 64 或者是 0 ，对于float而言，可能是32或64），s是一个字符串

* strconv.AppendBool(bs,b) 根据布尔变量b的值，在bs后追加True或者false字符
* strconv.AppendFloat(bs,f,fmt,prec,bit) 在bs后面追加浮点数f，其他参看strconv.Format.FLoat()函数
* strconv.AppendInt(bs,i,base) 根据base指定进制在bs后追加int64数字i
* strconv.AppendQuote(bs,s) 使用strconv.Quote()追加s到bs后面
* strconv.AppendQuoteRune(bs,char) 根据strconv.Quote()追加s到bs后面
* strconv.AppendQuateRuneToASCII(bs,char) 使用strconv.QuateRUneToASCII(char)追加char到bs后面
* strconv.AppendUInt(bs,u,base) 将uint64类型的变量u按照指定的进制base追加到bs后面
* strconv.Atoi(s) 返回转换后的int 类型值和一个error（出错时error不为空），可参考strconv.ParseInt()
* strconv.CanBackquote(s) 检查s是否一个符号Go语言语法的字符串常量，s中不能出现反引号
* strconv.FormatBool(tf) 格式化布尔变量tf，返回“true”或“false”字符串
* strconv.FormatFloat(f,fmt,prec,bit) 
* strconv.FormatInt(i,base) 将整数i以base指定的进制形式转换成字符串
* strconv.FormatUInt(u,base) 将整数i以base指定的进制形式转换成字符串
* strconv.IsPrint(c) 判断c是否为可打印字符
* strconv.Itoa(i) 将十进制数i转换成字符串，可参考strconv.FormatInt()
* strconv.ParseBool(s) 如果s是1，t,T TRUE,T则返回true和nil，如果s是0，f，F，false False，FALSE则返回false和nil 否则返回false和一个error
* strconv.ParseFloat(s,bits) 如果s能够转换成浮点数，则返回一个float64类型的值和nil，否则返回0和error；bits应该是64但是如果想转换成float32的话可以设置为32
* strconv.ParsetInt(s,base,bits)
* strconv.ParseUint(s,base,bits)
* strconv.Quote(s) 使用go语言单引号字符语法来表示一个rune类型的unicode 码字符char
* strconv.QuoteRuneToASCII(char) 
* strconv.QuoteToASCII(s ) 通strconv.Quote(),但是对非ASCII码字符进行转义
* strconv.Unquote(s) 对于一个用go语法如单引号，双引号，反引号等表示的字符或者字符串，返回英豪中的字符串和一个error变量
* strconv.UnquoteChar(s,b)  一个rune，一个bool，一个string以及一个error

#utf8 包

使用utf8包里的函数需要在程序中导入“unicode/utf”,变量b是一个[]byte类型的切片，s是字符串，c是一个rune类型的unicode编码

* utf8.DecodeLastRune(b) 返回b中最后一个rune和它占用的字节数，或者U+FFFD（unicode替换字符）和0，如果b最后一个rune是非法的话同上，但它输入的字符串
* utf8.DecodeRune(b) 返回b中的第一个rune和它占用的字节数，或者U+FFFD和0，如果b开始rune是非法的话同上，但它输入的是字符串
* utf8.DecodeRuneInString(s) 同上，但它输入的字符串
* utf8.EncodeRune(b,c) 将c作为一个UTF-8字符串并返回写入的字节数（b必须有足够的存储空间）
* utf8.FullRune(b) 如果b的第一个rune是UTF-8编码的话，则返回true
* utf8.FullRuneInString(b) 如果s的第一个rune是utf-8编码的话，返回true
* utf8.RuneCount(b) 返回b中的rune个数，如果存在非ASCII字符的话这个值可能小于len(s)
* utf8.RuneCountInString(s) 同上，但是她输入的是字符串
* utf8.RuneLen(c) 对c进行编码需要的字节数
* utf8.RuneStart(x) 如果x可以作为一个rune的第一个字节的话，返回true
* utf8.Valid(b) 如果b中的字节能正确表示一个UTF-8字符串，返回true
* utf8.ValidString(s) 如果s中的字节能正确表示一个UTF8编码的字符串，返回true

#unicode包
变量c是一个rune类型的变量，表示一个Unicode码点

* unicode.Is(table,c) 如果c在table中，返回true
* unicode.IsControl(c) 如果c是一个控制字符，返回true
* unicode.IsDigit(c) 如果c是一个十进制数字。返回true
* unicode.IsGraphic(c) 如果c是一个图形字符，如字母，数字，标记，符合，或者空格，返回true
* unicode.IsLetter(c) 如果c是一个字母，返回true
* unicode.IsLower(c) 如果c是一个小写字母 返回true
* unicode.IsMark(c) 如果c是一个标记，返回True
* unicode.IsOneOf(tables,c) 如果c在tables中的任一个table中，返回true
* unicode.IsPrint(c) 如果c是一个可打印字符，返回True
* unicode.IsPunct(c) 如果c是一个标点符号，返回true
* unicode.IsSpace(c) 如果c是一个空格，返回true
* unicode.IsSymbol(c) 如果c是一个符号，返回true
* unicode.IsTitle(c) 如果c是一个标题大写的字符，返回true
* unicode.IsUpper(c) 如果c是一个大写字母，返回true
* unicode.To(case,c) 字符c的case版本，其中case可以是unicode.LowerCase，unicode.TitleCase 或者unicode.UpperCase
* unicode.SimpleFold(c) 在与c的码点等价的码点集中，该方法返回最小的大于等于c的码点，否则如果不存在与其等价的码点，则返回最小的大于等于0的码点
* unicode.ToLower(c) 字母c的小写形式
* unicode.ToTitle(c) 字符c的标题形式
* unicode.ToUpper(x) 字母c的大写形式
	> unicode没有判断十六进制数的的方法不过可以自己实现
		func IsHexDigit（char rune）bool {
			return unicode.Is(unicode.ASCII_HEX_Digit,char)
		}

#regexp包
变量p和s都是字符类型，p表示正则匹配的模式

* regexp.Match(p,b)  如果[]byte类型的b和模式p匹配，返回true和nil
* regexp.MatchString(p,s) 如果s和模式p匹配，返回true和nil
* regexp.MatchReader(p,s) 如果从r中读取和模式p匹配，返回true和nil，r是一个io.RuneReader
* regexp.QuoteMeta(s) 用引号安全的括起来的与正则表达式元字符相匹配的字符串
* regexp.Compile(p) 如果模式p编译成功，返回一个*regexp.Regexp和nil
* regexp.CompilePOSIX(p) 如果模式p编译成功，返回一个regexp.Regexp和nil
* regexp.MustCompile(p) 如果模式p编译成功，返回一个regexp.Regexp和nil
* regexp.MustCompilePOSIX(p) 如果模式p编译成功，返回一个regexp.Regexp和nil

rx是*regexp.Regexp类型的变量，s是用以匹配的字符串，b是用以匹配的字节切片，r是用以匹配的io.RuneReader类型变量，n是最大匹配次数，返回nil表示没有匹配成功

* rx.Expand(...) 由ReplaceAll（）方法执行$替换，很少直接使用
* rx.ExpandString(...) 由ReplaceAllString() 方法执行$替换，很少直接使用
* rx.Find(b) 使用最左匹配策略返回一个[]byte类型的切片或者nil
* rx.findAll(b) 返回所有非重叠匹配的[][]byte类型的切片或者nil
* ....下次接着总结

#sort包
* sort.Float64s(fs) 将[]float64按升序排序
* sort.Float64AreSorted(fs) 如果[]float64是有序的则返回True
* sort.Ints(is) 将[]int按升序排序
* sort.IntAreSorted(is) 如果[]int是有序的则返回true
* sort.IsSorted(d) 如果sort.Interface的值d是有序的，则返回True
* sort.Search(size,fn) 在一个排序好的数组中根据函数签名为func(int) bool的函数fn进行搜索，返回第一个使得函数fn返回值为true的索引
* sort.SearchFloat64s(fs,f) 返回有序[]float64切片fs中类型为float64的值f的索引值
* sort.SearchInts(is,i) 返回有序[]int切片is中类型为int的值i的索引
* sort.SearchStrings[ss,s] 返回有序[]string切片ss中类型为string的值s的索引
* sort.Sort(d) 排序类型为sort.Interface的切片
* sort.Strings(ss) 按升序排序[]string类型的切片ss
* sort.StringAreSorted(ss) 如果[]string类型的切片ss是有序的，返回true


