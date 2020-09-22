fun stupidTest(){
    sequenceOf(1, 2, 3).flatMap { listOf(it, it * 2) }
}