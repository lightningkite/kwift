//Package: com.test
//Converted using Khrysalis2


class Record {
    x: number;
    y: string;
    
    public constructor(x: number, y: string) {
        this.x = x;
        this.y = y;
        console.log("Record created: ${x}, ${y}")
    }
    
    test(): void {
        console.log("Test run")
    }
    companion object { 
         public theMeaning;
         make(x: number, y: string): Record {
            return new Record(x, y);
        }
         
 }}
 
 

export function main(): void {
    Record.theMeaning.test();
    Record.make(43, "One more").test()
}
 
