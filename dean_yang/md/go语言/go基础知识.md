#数据类型
###整数

	带符号： int int8 int16 int32 int64
	不带符号： uint unit8 uint16 uint32 uint64
	不同类型必须强制转换：
		var value2 int32
		value1 := 64 //value1将会自动推导为int类型
		value2 = value1 //编译错误
		value2 = int32（value1） //正确

	两个不同类型的数值不能直接比较

还有2个特殊的整型别名 ，和字符串很相关，分别是：byte(uint8)，rune(int32)，到字符串相关时在做解释
 
整数比较用 > / < / == / != / <= / >=，结果是布尔类型， true or false
 
按位运算 &（且）， |（或）， ^（异或），&^（与非），<<（左移），>>（右移）
异或自身，相当于取反码， 例如 ^1 = -2（ps：(^-1) + 1 = 求-1的补码）

###浮点数

	float32 float64
	浮点数不要比较，有一种替换方案
	import "math"
	//p为用户自动义的比较精度，比如0.00001
	func IsEqual(f1,f2,p float64) bool {
		return math.Fdim(f1,f2) < p 
	}
###布尔
 
	true  false
三种逻辑运算，&&，||，！
布尔类型不支持其他类型的转换

###字符串
	string
	var str string
	str = "hello"
	ch ：= str[0]
	len(str)
字符串操作：
	x+y 字符串连接 "hello"+"123" 
	len(s) 字符串长度 len("hello") //结果为5
	s[i] 取字符   "hello"[1] //结果为'e'
更多strings包

字符串遍历：
	str := "hello,十二届"
	n := len(str)
	for i := 0; i<n;i++ {
		ch := str[i]	//以字节数组遍历，byte类型
		fmt.Println(i,ch)
	}	
	//以unicode字符遍历
	str := "hello,世界"
	for i,ch := range str {
		fmt.Println(i,ch) //ch的类型为rune
	}

组成字符串的最小单位是字符，存储的最小单位是字节，字符串本身不支持修改。

###字符类型
	byte实际上是unit8的别名，代表UTF-8的单个字节的值
	rune代表Unicode字符


###错误类型
	error

###指针
	pointer

###数组
常规的数组声明方法：
	[32]byte
	[2*N] struct {x ,y int32} //复杂数组类型
	[1000]*float64 //指针数组
	[3][5]int	//二维数组
	[2][2][2]float64 
一旦数组被声明了，那么它的数据类型跟长度都不能再被改变
	获取数组arr元素的个数
	arrlength := len(arr)
元素访问：
	for i := 0; i <len(array);i++ {
		fmt.Println("Element",i,"of array is",array[i])
	}
	
	for i,v :=- range array {
		fmt.Println("Array element[",i,"]=",v)
	}
数组切片：
	package main
	import (
		"fmt"
	)
	func main() {
		//先定义一个数组
		var myArray [5]int = [5]int{1,2,3,4,5}
		//基于数组创建一个数组切片
		var mySlice []int = myArray[:3]
		fmt.Println("Elements of myArray: ")

		for _,v := range myArray {
			fmt.Print(v," ")
		}

		fmt.Println("\nElements of mySlice: ")
		for _,v := range mySlice {
			fmt.Print(v," ")
		}
		fmt.Println()
	}
	基于myArray的所有元素创建数组切片：
	mySlice = myArray[:]
	基于myArray的前3个元素创建数组切片：
	myslice := myArray[:3]
	基于myArray的第3个元素创建数组切片：
	myslice := myArray[3:]
	
	直接创建数组切片：
	mySlice1 ：= make([]int,5) //创建一个初始元素为5的数组切片，元素切片初始值0
	mySlice2 := make([]int,5,10)  //创建初始元素个数为5的数组切片，元素初始值0,预留10个元素
	mySlice3 := []int{1,2,3,4,5}  //直接创建并初始化包含5个元素
	元素遍历：
	for i := 0; i<len(mySlice);i++{
		fmt.Println("mySlice[",i,"]=",mySlice[i])
	}
	使用range遍历：
	for i,v := range mySlice {
		fmt.Println("mySlice[",i,"] =",v)
	}
	空间换时间：
	cap()函数返回数组切片分配的空间大小，len()返回数组切片中当前所存储的元素个数
	package main

	import "fmt"
	func main(){
		mySlice := make([]int,5,10)
		fmt.Println("len(mySlice):",len(mySlice)) //返回5
		fmt.Println("cap(mySlice):",cap(mySlice)) //返回10
	}
	如果需要往上例中mySlice已包含的5个元素后面继续新增元素，使用append()
	mySlice = append(mySlice,1,2,3)
	直接添加一个数组：
	mySlice2 ：= []int{8,9,10}
	mySlice = append(myslice,myslice...) //...相当于把mySlice2包含的元素打散后传入
	
	基于数组切片创建数组切片：
	oldSlice := []int{1,2,3,4}
	newSlice := oldSlice[:3]

	内容复制：
	slice1 := []int{1,2,3,4,5}
	slice2 := []int{5,4,3}
	copy(slice2,slice1) //只会复制slice1的前3个元素到slice中
	copy(slice1,slice2) //只会复制slice2的前3个元素到slice1的前3个位置

数组在GO语言中是一个值类型。所有值类型变量在函数中作为参数传递时都将产生一次复制动作。数组作为函数的参数类型，修改数组将不会成功
	

	
var array [5]int   



// 声明一个长度为5的整数数组
// 初始化每个元素

array := [5]int{7, 77, 777, 7777, 77777}

如果你把长度写成 ...，Go 编译器将会根据你的元素来推导出长度：

// 通过初始化值的个数来推导出数组容量

array := [...]int{7, 77, 777, 7777, 77777}

// 声明一个长度为5的整数数组

// 为索引为1和2的位置指定元素初始化

// 剩余元素为0值

array := [5]int{1: 77, 2: 777}

array := [5]*int{0: new(int), 1: new(int)}

// 为索引为0和1的元素赋值

*array[0] = 7

*array[1] = 77

###常量
	const (
	        e  = 2.71
	        pi = 3.14
	)

	 type Weekday int
	 const (
	         Sunday Weekday = iota
 	         Monday
     	     Tuesday
    	     Wednesday
    	     Thursday
	         Friday
	         Saturday
	       )
常量的值不可修改

数组指针：
	//给数组清零
	func zero(ptr *[32]byte) {	
	for i := range ptr {
		ptr[i] = 0
		}
	}
	
	func zero(ptr *[32]byte) {
		*ptr = [32]byte{}
	}

###map
map是一堆键值对的未排序集合。
	package main
	import "fmt"
	
	type PersonInfo struct {
		ID string
		Name string
		Address string
	}
	
	func main(){
		var personDB map[string] PersonInfo
			personDB = make(map[string] PersonInfo)
			//往这个map里插入几条数据、
			personDB["12345"] = PersonInfo{"12345","Tom","Room 203,..."}
			personDB["1"] = PersonInfo{"12345","Tom","Room 203,..."}
			//从这个map查找键为“1234”的信息
			person，ok := personDB["1234"]
			//ok是一个bool类，返回true表示找到
			if ok {
				fmt.Println("Found person",person.name,"with ID 1234.")
			} else {
				fmt.Println("Did not find person with ID 1234")
			}
			
	}
1.变量声明
	var myMap map[string] PersonInfo
	myMap是声明的map变量，string是键的类型，personInfo则是其中所存放的值类型
2.创建
	myMap = make(map[string] PersonInfo)
	创建指定该map的初始存储能力：
	myMap = make(map[string] PersonInfo,100)
	创建并且初始化：
	myMap = map[string] PersonInfo{
		"1234": PersonInfo{"1","jack","Room 1 ..."}
	}
3.元素赋值
	personDB["12345"] = PersonInfo{"12345","Tom","Room 203,..."}
4.元素删除
	delete(myMap,"1234") //如果这个键不存在，什么都不发生，但如果map变量的值是nil，该调用抛出异常
	


###Slice
切片不能比较，与数组不同，不能使用==操作符判断两个slice是否含有全部相等的元素。
定义切片：
	s := []int{0,1,2,3,4,5}
使用slice：
	s[:2]
	s[:]
切片操作不能超过cap的上限，超过将会导致一个panic异常，但是超过len意味着扩展

###条件语句
展示：
	if a<5{
		return 0
	} else {
		return 1
	}
	
#选择语句：switch语句

1.最简单的switch语句

	switch content {
	default:
		fmt.Println("unkown")
	case "python":
		fmt.Println("a interpreter language!")
	case "Go":
		fmt.Println("a compiled language")
	}

2.使用函数返回值作为条件

	switch content := getContent(); content {
	default: 
		fmt.Println("unkown")
	case "python":
		fmt.Println("a interpreter language")
	case "Go":
		fmt.Println("a compile language")
	}


3.关键字fallthrough，break,多个条件“，”

	switch content：=getContent；content {
	default：
		fmt.Println("unkonw")
	case "Ruby":
		fallthrough
	case "python":
		fmt.Println("a interpreted Language")
	case "break":
		break
	case "C","java","GO":
		fmt.Println("A compile language")
	}

4.类型匹配

	switch v.(type) {
	case string:
		fmt.Printf("The string is '%s' .\n", v.(string))
	case int, uint, int8, int16, int32, uint32,uint64,int64:
		fmt.Printf("The integer is %d. \n",v)
	default:
		fmt.Printf("Unsupport value. (type=%T)\n",v)
	}	


	switch i := v.(type) {
	case string:
		fmt.Printf("The string is '%s' .\n",i)
	case int, uint:
		fmt.Printf("The integer is %d .\n",i)
	default:
		fmt.Printf("Unsupport value. (type=%T) \n",i)
	}

5.更多惯用法

	switch {
	case number < 100:
		number++
	case number < 200:
		number--
	default:
		number -= 2
	}

###for语句
1.组成和编写方法
	for number < 200 {
	number += 2
	}
	//死循环
	for {
		number++
	}

2.for字句
	
	for i := 0;i < 100;i++ {
		number++
	}
	
	var j uint = 1
	for ;j%5 !=0; j *= 3 {	//省略初始化字句
		number++
	}

	for k := 1; k%5 != 0; { //省略后置语句
		k *= 3
		number++
	}

3.range字句
	ints := []int{1,2,3,4,5,}
	for i,d := range ints {
	fmt.Printf("%d: %d\n",i,d)
	}	

	var i,d int
	for i,d = range ints {
	 fmt.Printf("%d:%d\n",i,d)	
	}

	ints := []int{1,2,3,4,5}
	length := len(ints)
	indexesMirror := make([]int,length)
	elementsMirror := make([]int,length)
	var i int
	for indexesMirror[length-i-1],elementsMirror[length-i-1] = range ints {
	 i++
	}

	ints := []int{1,2,3,4,5}
	for i := range ints{
	d := ints[i]
	fmt.Printf("%d: %d\n",i,d)
	}

4.更多惯用法
	m := map[unit]string{1: "A",6:"C",7:"B"}
	var maxKey uint
	for k := range m {
		if k > maxKey {
			maxKey = k
		}
	}

###跳转语句
goto语句：
	func myFunc(){
		i := 0
		HERE:
		fmt.Println(i)
		i++
		if i < 10 {
			goto HERE
		}
	}

###函数
1.函数定义
	package mymath
	import "errors"
	
	func Add(a int,b int) (ret int,err error) {
		if a<0 || b<0 {
			err = error.New("should be non-negative numbers!")
			return
		}
		return a+b,nil //支持多重返回值
	}
	//如果参数列表中相邻的参数类型相同,省略前面的变量类型声明
	func Add(a,b int) (ret int,err error){
		///...
	}
	如果只有一个返回值，也可以这么写：
	func Add(a,b int) int {
		// ...
	}
2.函数调用
事先导入该函数的包
	import "mymath"
	
	c := mymath.Add(1,2)

3.不定参数
将函数定义为不定参数类型
	func myFunc(args ...int) {
		for _,arg := range args {
			fmt.Println(arg)
		}
	}
	//函数myFunc()接受不定数量的参数，这些参数的类型全部是int，调用方式如下：
	myFunc(2,3,4)
	myFunc(1,5,7,34)
	//...type本质上是一个数组切片，[]type
	
	不定参数传递：
	func myFunc（args ...int）{
		//按原样传递
		myFunc3(args...)
		//传递片段，实际上任意的int slice都可以传递进去
		myfunc3（args[1:]...）
	}
4,任意类型的不定参数
指定类型为interface{},以下是printf的函数原型：
	func Printf(format string,args ...interface{}){
		//...
	}

	package main

	import "fmt"

	func MyPrintf(args ...interface{}) {
		for _,arg := range args {
			switch arg.(type) {
				case int:
					fmt.Println(arg,"是整数")
				case string:
					fmt.Println(arg,"是字符串")
				case int64:
					fmt.Println(arg,"是int64类型")
				defalut:
					fmt.Println(arg,"不知道")
			}
		}
	}

	func main() {
		var v1 int = 1
		var v2 int64 = 234
		var v3 string = "hello"
		MyPrint(v1,v2,v3)
	}
###匿名函数和闭包
匿名函数由一个不带函数声明和函数体组成：
	func(a,b int,z float64) bool {
		return a*b < int(z)
	}
匿名函数可以直接复制给一个变量或者直接执行：
	f := func(x,y int) int {
		return x + y
	}

	func(ch chan int) {
		ch <- ACK
	}(reply_chan) //花括号后直接跟参数列表表示函数调用
GO语言中闭包：
	package main

	import (
		"fmt"
	)
	
	func main() {
		var j int = 5
		a := func()(func ()){
			var i int =10
			return func() {
				fmt.Printf("i,j: %d,%d\n",i,j)
			}
		}()
		a()
		j *= 2
		
		a()
	}
	输出结果： i,j : 10,5
			  i,j : 10,10
	变量a指向的闭包函数引用了局部变量i和j的值，i的值被隔离，在闭包外不能被修改，改变j的值以后，发现结果是修改过的值

###错误处理
error接口
	type error interface {
		Error() string
	}

	func Foo(param int)(n int,err error) {
		//...
	}
	调用时的代码建议按如下方式处理错误情况：
	n, err := Foo(0)
	
	if err != nil {
		//错误处理
	}else{
		//使用返回值n
	}
自定义error类型：
	type PathError struce {
		Op string
		Path string
		Err error
	}

	func (e *PathError) Error() string {
		return e.Op + " " + e.Path + ":" + e.Err.Error() 
	}
	//使用PathError
	func Stat（name string） (fi FileInfo, err error) {
		var stat syscall.Stat_t
		
		err = systcall.Stat(name, &stat)
		if err != nil {
			return nil, &PathError{"stat",name,err}
		}
		return fileInfoFromStat(&stat, name),nil
	}
	//如果处理错误获取详细信息不满足与打印一句错误信息，类型转换
	fi, err := os.Stat("a.txt")

	if err != nil {
		if e,ok := err.(*os.PathError); ok && e.Err != nil {
			//获取patherror类型吧变量中e中的其他信息并处理
		}
	}
	
###defer关键字
	func CopyFile(dst,src string) (w int64,err error) {
		srcFile,err := os.Open(src)
		if err != nil {
			return
		}
		defer srcFile.Close()
		dstFile,err := os.Create(dstName)
		if err != nil {
			return 
		}
		defer dstFile.Close()
		return io.Copy(dstFile,srcFile)
	}
	即使Copy函数抛出异常，依然可以保证dstFile和srcFile会正常关闭，如果觉得一句话干不完清理的工作，使用一个匿名函数：
	defer func() {
		//做你复杂的清理工作
	}()

###panic()和recover()
这两个内置函数报告和处理运行时错误和程序中的错误场景：
	func panic(interface{})
	func recover(interface{})
	当在一个函数执行过程中调用panic()函数时，正常的函数执行流程将立即终止执行，但defer关键字延迟执行的语句将正常执行
	之后该函数返回到调用函数，并逐层向上执行panic流程，直至所有的goroutine中所有正在执行的函数被终止。错误信息将会被报告，包括在调用panic()函数时传入的参数,这个过程称为错误处理流程。
	panic(404)
	panic("network broken")
	panic(Error("file not exists"))
	
	recover()函数用于终止错误处理流程。
	defer func() {
		if r := recover(); r != nil {
			log.Printf("Runtime error caught: %v",r)
		}
	}()
	foo()
	无论foo（）中是否触发了错误处理流程，该匿名defer函数都将在函数退出时得到执行，如果foo中触发了错误处理流程，recover函数执行将使得作物处理过程终止。如果错误处理流程被触发时，程序传给panic函数的参数不为nil，则该函数还会打印详细的错误信息

###为类型添加方法
	type Integer int
	func (a Integer) Less(b Integer) bool {
		return a < b
	}

	func main(){
		var a Integer = 1
		if a.less(2){
			fmt.Println(a,"Less 2")
		}
	}
GO语言中没有隐藏的this指针：1.方法施加的目标（“对象”）显示传递，没有被隐藏 2.方式施加的目标（“对象”）不需要非得是指针，也不用非得叫this

	func (a *Integer) Add(b Integer) {
		*a += b
	}
	由于Add（）方法需要修改对象的值，所以需要用指针引用
	//调用
	func main() {
		var a Integer = 1
		a.Add(2)
			fmt.Println("a = ",a)
	} //输出3

###值引用和引用语义
	b = a
	b.Modify()
如果b的修改不会影响a的值，name此类型属于值类型，如果会影响a的值，name此类型是引用类型。数组是值类型

	var a = [3]int{1,2,3}
	var b = a 
	b[1]++
	fmt.Println(a,b)
	//[1 2 3] [1 3 3]
如果想表达引用，需要用指针：
	var a = [3]int{1,2,3}
	var b = &a
	b[1]++
	fmt.Println(a,b) 
	//[1 3 3] [1 3 3]
GO语言有四个类型比较特别，看起来像引用类型：
	数组切片
	map
	channel
	接口

###结构体
struct和其他语言的类（class）有同等的地位，没有继承，担忧组合

	type Rect struct {
		x,y float64
		width,height float64
	}

	func (r *Rect) Area() float64 {
		return r.width * r.height
	}
初始化
	rect1 := new(Rect)
	rect2 := &Rect{}
	rect3 := &Rect{0,0,100,200}
	rect4 := &Rect{width:100,height:200}
	//未显示初始化的变量都会被初始化为0值，false，空字符串
GO语言中没有构造函数，对象的创建通常交由一个	全局的创建函数来完成，以NewXXX类命名，表示构造函数
	func NewRect((x,y,width,height float64) *Rect{
		return &Rect{x,y,width,height}
	}
###匿名组合
GO语言的继承，称为匿名组合
	type Base struct {
		Name string
	}
	
	func (base *Base) Foo() {...}
	func (base *Base) Bar() {...}

	type Foo stuct {
		Base
		...
	}
	
	func (foo *Foo) Bar() {
		foo.Base.Bar()
		...
	}
以上代码定义一个Base类（实现了Foo()和Bar()方法两个成员方法），然后定义Foo类，该类从Base类“继承”并改写Bar()方法（该方法实现时先调用了基类的Bar()方法）

在派生类Foo没有改写基类Base的成员方法时,相应的方法就被继承，例如上面例子中，调用foo.Foo()和调用foo.Base.Foo()效果一致
	还可以从指针方式从一个类型“派生”
	type Foo struct {
		*Base
		...
	}
这段代码任然有派生效果，只是Foo创建实例，需要从外部提供一个Base类的实例指针
	例如：匿名组合一个log.Logger指针
	type Job struct {
		Command string
		*log.Logger
	}
在合适的赋值之后，Job类型中所有的成员方法可以很舒适的借用所有log.Logger提供的方法。比如：
	func (job *Job) Start() {
		job.Log("starting now ...")
		...//做一些事情
		job.Log("started.")
	}
	
###可见性
要使某个符号对其他包可见需要将符号定义为以大写字母开头：
	type Rect struct {
		Y,X float64
		Width,Height float64
	}
Rect类型的成员变量全部被导出，可以被所有其他引用了Rect所在包的v代码访问到。
	func(r *Rect) area float64 {
		return r.Width * r.Height
	}
Rect的area()方法只能在该类型所在的包内访问。但是同一个包中的其他类型可以访问。

###接口
非侵入是接口：一个类只要实现了接口要求的所有函数没我们就说这个类实现了该接口
	type File struct {
		//...
	}
	
	func (f *file) Read(buf []byte) (n int,err error)
	func (f *file) write(buf []byte) (n int,err error)	
	func (f *file) Seek(buf []byte) (n int,err error)
	func (f *file) Close() error
这里我们定义一个File类，并实现了Read(),Write(),Seek(),Close().假设我们有如下接口：
	type IFile interface {
		Read(buf []byte) (n int,err error)
		write(buf []byte) (n int,err error)
		Seek(buf []byte) (n int,err error)
		Close() error
	}

	type IReader interface {
		Read(buf []byte) (n int,err error)
	}

	type IWriter interface {
		write(buf []byte) (n int,err error)
	}
	
	type ICloser interface {
		Close() error
	}
将官File类并没有从这些接口继承，甚至不知道这些接口存在，但是File实现这些接口，可以进行赋值
	var file1 IFile = new(File)
	var file2 IReader = new(File)
	var file3 IWriter = new(File)
	var file4 ICloser = new(File)

###接口赋值
1. 将对象实例赋值给接口
2. 将一个接口赋值给另一个接口

对象实例实现了接口要求的所有方法：
	type Integer int
	func (a Integer) less(b Integer) bool {
		return a<b
	}
	
	func (a *Integer) Add(b Integer) {
		*a += b 
	}
相应的，定义接口lessAdder：
	type lessAdder interface {
		Less(b Integer) bool
		Add(b Integer)
	}
将Integer类型的对象实例，赋值给LessAdder接口：
	var a Integer = 1		
	var b LessAdder = &a
只要两个接口拥有相同的方法列表，name他们就是等同的，可以相互赋值

如果接口A的方法列表是接口B的方法列表的子集，那么接口B可以赋值给接口A

###接口查询
	var file1 Writer = ...
	if file5,ok := file1.(two.IStream);ok{
		...
	}
这个if语句检查file1接口指向的对象实例是否实现了two.IStream接口，如果实现了，则执行特定的代码。。。接口查询是否成功，运行期才能确定。

###类型查询

	var v1 interface{} = ...
	switch v := v1.(type) {
		case int: 
		case string:
	}

	// 另一个实现了 I 接口的 R 类型
	type R struct { i int }	
	func (p *R) Get() int { return p.i }
	func (p *R) Put(v int) { p.i = v }

	func f(p I) {
	    switch t := p.(type) { // 判断传递给 p 的实际类型	
	        case *S: // 指向 S 的指针类型
	        case *R: // 指向 R 的指针类型
	        case S:  // S 类型
	        case R:  // R 类型
	        default: //实现了 I 接口的其他类型
	    }
	}

类型查询配合接口查询使用：
	type Stringer interface {
		String() string
	}

	func Println(args ...interface{}) {
		for _, arg := range args{
			switch v := arg.(type){
				case int:
				case string:
				default:
				if v,ok := arg.(Stringer); ok {
					val := v.String()
					//...
				}else{
					//...
				}
			}
		}
	}


###接口组合
	type ReadWriter interface {
		Reader
		Writer	
	}
这个接口组合了Reader和Writer两个接口，她等价于如下写法：
	type ReadWriter interface {
		Read(p []byte) (n int, err error)
		Writer(p []byte) (n int, err error)
	}


###接口使用的3种方法
	http://blog.csdn.net/love_se/article/details/7947440

###Any类型
任何对象实例都满足空接口
	var v1 interface{} = 1
	var v2 interface{} = "abc"
	var v3 interface{} = &v2
	var v4 interface{} = struct{X int}{1}
	var v4 interface{} = &struct{X int}{1}
当函数可以任意的对象实例时，将其声明为interface：
	func Printf(fmt string,args ...interface{})
	func Println(args ...interface{})

###goroutine
goroutine是轻量级线程实现
	func Add(x,y int) {
		z := x+y
		fmt.Println(z)
	}

	go Add(1,1)
在一个函数调用前加上go关键字，这次调用就会在一个goroutine中并发执行，当调用被函数返回时，这个goroutine也自动结束了。需要注意，如果这个函数有返回值，那么这个返回值，会被丢弃。
	package　main

	import "fmt"

	func Add(x,y int) {
		z := x + y
		fmt.Println(z)
	}

	func main() {
		for i := 0; i < 10; i++{
			go Add(i,i)
		}
	}//无输出
###并发通信
	package main

	import(
		"fmt"
		"sync"
		"runtime"
	)
	var counter int = 0
	func Count(lock *sync.Mutex) {
		lock.Lock()
		counter++
		fmt.Println(z)
		lock.Unlock()
	}

	func main() {
		lock := &sync.Mutex{}
		
		for i := 0; i<10; i++ {
			go Count(lock)
		}

		for {
			lock.Lock()
			c := counter
			lock.Unlock()

			runtime.Gosched()
			if c >= 10 {
				break
			}
		}
	}
在10个goroutine中共享了变量counter。引入锁，每次对n操作，都先将锁锁住,操作完成再讲锁打开。很麻烦，但是Go源提供另一种通信模型，即消息机制而非共享内存作为通信机制。

###channel	
channel是类型相关的。一个channel只能传递一种类型的值。声明时指定类型
	package main
	import "fmt"

	func Count(ch chan int) {
		ch <- 1 //在每个goroutine的Add()函数完成后，向对应的channel写入一个数据，在这个channel被读取钱这个操作是阻塞的。
		fmt.Println("counting")
	}

	func main() {
		chs := make([]chan int,10) //定义包含10个channel的数组
		for i := 0; i<10; i++{
			chs[i] = make(chan int) //将每个channel分配给10个不同的goroutine
			go Count(chs[i])
		}
	
		for _,ch := range(chs) {
			<- ch //从10个channel中依次读取数据，在对应的channel写入数据前，这个操作阻塞
		}
	}
基本语法：
1. 声明形式：
	var chanName chan Type
	var ch chan int
	var m map[string] chan bool 声明一个map，元素类型是bool 
	ch := make(chan int) //声明并初始化一个int型的名为ch的channel
	ch <- value 数据写入到channel
	<- ch channel读取数据的语法
	
###select
unix时代 select()函数用来监控一系列文件句柄，一旦其中一个文件句柄发送了IO操作，该select()调用就会被返回。GO使用select关键字，用于处理异步IO问题
	select每个case语句必须是一个IO操作
	select {
		case <- chan1:
		//如果chan1成功读取到数据，则进行执行处理语句
		case chan2 <- 1:
		//如果成功向chan2写入数据，则进行处理语句
		default:
		//默认执行
	}

	ch := make(chan int,1)
	for {
		select {
			case ch <- 1:
			case ch <- 0:
		}
		i := <-ch
		fmt.Println("value received:",i)
	}  //死循环，实现随机向ch写入一个0或者1

###缓冲机制
带缓冲的channel
	c := make(chan int,1024) //即使没有读取方,写入方也可以一直往channel里写入，在缓冲区填满之前都不回阻塞
	读取：
	for i := range c{
		fmt.Println("Received:",i)
	}

###超时机制
使用select机制
	//实现并执行一个匿名函数的超时等待函数
	timeout := make(chan bool,1)
	go func() {
		time.Sleep(le9) //等待1秒钟
		timeout <- true
	}
	//然后把timeout这个channel利用起来
	select {
		case <-ch:
		//从ch读取数据
		case <-timeout:
		//一直没有从ch中读取数据，但从timeout中读取到了数据
	}
这样select机制可以避免永久等待的问题，因为程序会在timeout中获取到一个数据后继续执行，无论对ch的读取是否处于等待状态，从而达成1秒超时的效果。
###channel传递
	type PipeData struct {
		value int
		handler func(int) int
		next chan int
	}
写一个常规的处理函数，定义一系列PipeData的数据结构并一起传递给这个函数，可以达到流式处理数据的目的：
	func handler(queue chan *PipeData) {
		for data := range queue {
			data.next <- data.handler(data.value)
		}
	}

###单向channel
	var ch1 chan int //ch1是一个正常的channel，不是单向的
	var ch2 chan<- float64 //ch2是单向的channel，只用于写float64数据
	var ch3 <-chan int //ch3是单向channel，只用于读取int数据
	
	单向channel和双向channel之间的转换：
	ch4 := make(chan int)
	ch5 := <-chan int(ch4) //ch5单向读取
	ch6 := chan<- int(ch4) //ch6单向写入
	
	func Parse(ch <-chan int) {
		for value := range ch {
			fmt.Println("Parsing value",value)
		}
	}

###关闭channel
	close(ch)
	判断是否已经被关闭：
	x, ok := <-ch
	如果返回值false则表示ch已经被关闭

###多核并行化
计算N个整形数的总和，分成M分，M是CPU的个数。让每个CPU计算分给他的那份计算任务，最后将计算结果再做一次累加。
	type Vector []float64

	//分配给每个CPU的计算任务
	func (v Vector) DoSome(i,n int,u Vector,c chan int) {
		for ;i < n; i++{
			v[i] += u.Op(v[i])
		}
		c <- 1 //发信号告诉任务管理者我已经计算完成了
	}

	const NCPU = 16 //假设总共有16核

	func (v Vector) DoAll(u Vector) {
		c := make(chan int, NCPU) //用于接收每个CPU的任务完成信号
		for i := 0;i <NCPU; i++{
			go v.DoSome(i*len(v)/NCPU,(i+1)*len(v)/NCPU,u,c)
		}
		
		//等待所有的CPU任务完成
		for i:=0; i<NCPU;i++{
			<-c //获取到一个数据，表示一个cpu计算完成
		}
		//到这里表示所有计算已经结束
	}
通过设置环境变量GOMAXPROCS的值来控制使用多少个CPU核心，或者代码中启动goroutine之前先调用这个语句设置：
	runtime。GOMAXPROCS(16)

###出让时间片
	使用runtime包中的GoSched()函数实现控制何时主动出让时间片给其他goroutine

###同步锁
	sync包提供两种锁类型：sync.Mutex和sync.RWMutex
	Mutex比较暴力，当一个goroutine获得Mutex之后，其他职能等着释放。RWMutex友好些，单写多读,会阻止写，但不会阻止读。

###全局唯一性操作
	Once类型保证全局的唯一性操作
	var a string
	var once sync.Once

	func setup() {
		a = "hello"
	}

	func doprint() {
		Once.Do(setup)
		print(a)
	}
	func twoprint() {
		go doprint()
		go doprint()
	}
once的Do()方法保证了全局范围内只会调用指定函数一次，

