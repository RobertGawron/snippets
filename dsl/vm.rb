#!/usr/bin/env ruby 

class DSLVirtualMachine
    def initialize(file_name = String.new)
        @preprocesor = DSLPreprocesor.new file_name
        @stack = Array.new
        @memmory = Array.new
        @stack_ptr = [0]
        @memmory_ptr = [0]
    end

    def run()
        eval @preprocesor.parse()     # dynamic code loading
        @main.call()                  # code executing
    end

    private

    def alu(operation)
        parser = { 'add' => lambda { @stack.pop + @stack.pop },
                   'sub' => lambda { @stack.pop - @stack.pop },
                   'div' => lambda { @stack.pop / @stack.pop },
                   'mul' => lambda { @stack.pop * @stack.pop },
                   'gt'  => lambda { @stack.pop > @stack.pop }, }
        push parser[operation].call()
    end

    def call(function)
        @memmory_ptr.push @memmory.length
        function.call()
        @memmory_ptr.pop
    end

    def push(element)
        @stack.push element
    end

    def load(offset)
        memmory_ptr = @memmory_ptr.last
        @stack.push @memmory[offset+memmory_ptr]
    end

    def store(offset)
        memmory_ptr = @memmory_ptr.last
        @memmory[offset+memmory_ptr] = @stack.pop
    end

    def get_if_condition()
        return @stack.pop
    end

    def int(interrupt)
        parser = [ lambda { p @stack.pop },
                   lambda { push gets.to_i } ]
        parser[interrupt].call()
    end
end


class DSLPreprocesor
    attr_accessor :fname

    def initialize(fname = String.new)
        @fname = fname
        # input token => output token, order does matter
        @parser = {
            # remove comments
            /^([^;]*);.*$/  => 
                        lambda { |u| "#{u}" },
            # convert jump to label into 'if' statement
            # it's very LAME!
            /^[\s]*jz ([a-z0-9]*)$/ => 
                        lambda { |u| "if (get_if_condition)" },
            /^a[0-9]:*$/ => 
                        lambda { |u| "end" },

            # convert function label to declaration 
            # of proc in ruby ruby
            /^([a-z]*[0-9]*):$/  => 
                        lambda { |u| "@#{u} = Proc.new{" },
            # convert end of function in assembler 
            # to end of proc in in ruby 
            /^[\s]*ret$/  => 
                        lambda { |u| "}" },
            # 
            /^[\s]*call ([a-z0-9]*)$/  =>
                        lambda { |u| "call @#{u}" },

            # treat ALU operation in consistent way
            /^[\s]*(add|sub|mul|div|gt|lt|eq)$/ => 
                        lambda { |u| "alu '#{u}'" },
        }
    end

    def parse()
        file = File.readlines(self.fname)
        return file.map { |l| parse_mnemonic(l) }.join('')
    end

    private
    def parse_mnemonic(mnemonic)
        @parser.map { |k,v| mnemonic.gsub!(k){ v.call($1) } }
        return mnemonic
    end
end

# TODO use optparse instead of raw ARGV[0]
vm = DSLVirtualMachine.new ARGV[0]
vm.run()

