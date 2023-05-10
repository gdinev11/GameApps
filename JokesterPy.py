""" Jokester program
    CS C17 Event-Driven Programming """

from tkinter import *
from urllib.request import urlopen, Request
import json
from tkinter import messagebox

def get_momma_joke():
    resp = urlopen("https://api.yomomma.info/")
    joke_map = json.load(resp)
    messagebox.showinfo("Jokester", joke_map["joke"])
    
def get_dad_joke():
    req = Request("https://icanhazdadjoke.com/",
                    headers={"Accept": "application/json",
                           "user-Agent": "Jokester/0.1"})
    resp = urlopen(req)
    joke_map = json.load(resp)
    messagebox.showinfo("Jokester", joke_map["joke"])
    

root = Tk()
root.title("Jokester")

fr = Frame(root, width=1000, height=500)
fr.grid()

lbl = Label(fr, text="Get a Joke")
lbl.grid(column=0, row=0, columnspan=4, sticky=(W+E+N))
mom_btn = Button(fr, text="Yo Momma", command=get_momma_joke)
mom_btn.grid(column=0, row=1)
quit_btn = Button(fr, text="Quit", command=root.destroy)
quit_btn.grid(column=1, row=1, columnspan=2)
dad_btn = Button(fr, text="Dad Joke", command=get_dad_joke)
dad_btn.grid(column=3, row=1)

root.mainloop()